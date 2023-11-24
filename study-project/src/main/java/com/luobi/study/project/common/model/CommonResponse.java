package com.luobi.study.project.common.model;


import lombok.Data;

@Data
public class CommonResponse<T> {
    private boolean success;
    private CommonResponseCode code;
    private String message;
    private T result;
    private String errorCode;

    public CommonResponse(T result) {
        this.success = true;
        this.code = CommonResponseCode.SUCCESS;
        this.result = result;
    }

    public CommonResponse(CommonResponseCode code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
    }

    public CommonResponse(CommonResponseCode code, String message, T data) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.result = data;
    }

    public CommonResponse() {
        this.success = true;
        this.code = CommonResponseCode.SUCCESS;
    }

    public CommonResponse(boolean success, CommonResponseCode code, String message, T result, String errorCode) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
        this.errorCode = errorCode;
    }

    public CommonResponse(boolean success, CommonResponseCode code, String message, String errorCode) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static <K> CommonResponse<K> success(K data) {
        return new CommonResponse<>(data);
    }

    public static <K> CommonResponse<K> success() {
        return new CommonResponse<>();
    }

    public static <K> CommonResponse<K> success(boolean success, CommonResponseCode code, String message, K result, String errorCode) {
        return new CommonResponse<>(success, code, message, result, errorCode);
    }

    public static <K> CommonResponse<K> fail(String errorCode, String message) {
        return new CommonResponse<>(false, CommonResponseCode.OPERATION_FAILED, message, errorCode);
    }

    public static <K> CommonResponse<K> fail(CommonResponseCode code, String message) {
        return new CommonResponse<>(code, message);
    }

    public static <K> CommonResponse<K> fail(CommonResponseCode code, K result) {
        return new CommonResponse<>(code, "", result);
    }

    public static <K> CommonResponse<K> fail(CommonResponseCode code, String message, K result) {
        return new CommonResponse<>(code, message, result);
    }

    public static <K> CommonResponse<K> fail(CommonResponseCode code) {
        return new CommonResponse<>(code, code.name());
    }

}
