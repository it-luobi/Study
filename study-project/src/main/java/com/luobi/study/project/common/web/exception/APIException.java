package com.luobi.study.project.common.web.exception;


import com.luobi.study.project.common.model.CommonResponseCode;
import lombok.Getter;
import lombok.Setter;

public class APIException extends RuntimeException {

    @Getter
    @Setter
    private CommonResponseCode errorCode = CommonResponseCode.API_ERROR;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public APIException(CommonResponseCode errorCode) {
        this.errorCode = errorCode;
    }

    public APIException(String message, CommonResponseCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public APIException(String message, Throwable cause, CommonResponseCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public APIException(Throwable cause, CommonResponseCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return getCause() == null ? super.getMessage() : getCause().getMessage();
    }

}
