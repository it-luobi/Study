package com.luobi.study.project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

@Slf4j
public class ReflectionUtils {

	public static String getFieldString(Object o, String fieldName) {
		try {
			Field field = FieldUtils.getField(o.getClass(), fieldName, true);
			if (field == null) {
				return null;
			}
			Object fieldValue = field.get(o);
			return fieldValue == null ? null : String.valueOf(fieldValue);
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	public static Object getFieldValue(Object o, String fieldName) throws IllegalAccessException {
		Field field = FieldUtils.getField(o.getClass(), fieldName, true);
		if (field == null) {
			return null;
		}
		return field.get(o);
	}

	public static void setField(Object o, String fieldName, Object value) {
		try {
			FieldUtils.writeField(o, fieldName, value, true);
		} catch (ReflectiveOperationException | RuntimeException e) {
			log.warn("set field error", e);
		}
	}

	public static Object newInstance(Class<?> cls) {
		Object instance;

		try {
			instance = cls.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException | RuntimeException e) {
			log.error("new instance failure", e);
			throw new RuntimeException(e);
		}
		return instance;
	}
}
