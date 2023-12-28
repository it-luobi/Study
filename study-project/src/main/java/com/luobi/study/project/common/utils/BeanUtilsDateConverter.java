package com.luobi.study.project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.beanutils.ConversionException;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@SuppressWarnings("unchecked")
public class BeanUtilsDateConverter implements Converter {

	private static final String[] SUPPORT_FORMATS = {DateUtils.DATE_FORMAT, DateUtils.DATE_TIME_FORMAT,
		DateUtils.DATE_TIME_WITH_MS_FORMAT};

	@Override
	public Object convert(Class type, Object value) {
		if (type != Date.class) {
			return value;
		}

		if (value == null || Objects.equals(StringUtils.EMPTY, value)) {
			return null;
		}

		if (value instanceof Date) {
			return value;
		}

		if (value instanceof String) {
			return Optional.of(value.toString().trim())
				.filter(StringUtils::isNotBlank)
				.map(dateStr -> {
					int length = dateStr.length();
					try {
						for (String format : SUPPORT_FORMATS) {
							if (length == format.length()) {
								return DateUtils.parse(dateStr, format);
							}
						}
					} catch (RuntimeException e) {
						throw new ConversionException("invalid value format, value is " + value);
					}
					throw new ConversionException("invalid value format, value is " + value);
				})
				.orElse(null);
		}

		if (value instanceof Map) {
			return Optional.ofNullable(((Map<?, ?>)value).get("json.expansion@value"))
				.map(String::valueOf)
				.filter(StringUtils::isNotBlank)
				.map(time -> {
					try {
						return new Date(Long.parseLong(time));
					} catch (RuntimeException e) {
						throw new ConversionException("invalid value format, value is " + value);
					}
				})
				.orElse(null);
		}
		throw new ConversionException("invalid value type, class is " + value.getClass());
	}

}
