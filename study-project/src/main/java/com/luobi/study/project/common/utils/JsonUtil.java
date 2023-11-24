package com.luobi.study.project.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
	}

	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("JsonUtil#toJson Exception - {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static String toJsonForDateFormat(Object obj) {
		try {
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("JsonUtil#toJson Exception - {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(String json, TypeReference<T> type) {
		try {
			return objectMapper.readValue(json, type);
		} catch (IOException e) {
			logger.error("JsonUtil#fromJson Exception - {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.error("JsonUtil#readValue Exception - {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
