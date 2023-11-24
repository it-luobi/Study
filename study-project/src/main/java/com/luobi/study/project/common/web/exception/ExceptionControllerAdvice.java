package com.luobi.study.project.common.web.exception;

import com.luobi.study.project.common.model.CommonResponse;
import com.luobi.study.project.common.web.RestTemplateContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    @ResponseBody
    public CommonResponse<?> handleAPIException(APIException e) {
        return RestTemplateContextHolder.getRestTemplateExecutionInfo()
                .map(executionInfo -> {
                    String logMessage = executionInfo.toLogMessage();
                    log.error("Exception occurred while calling API : {}", logMessage);
                    String exceptionMessage = executionInfo.toExceptionMessage();
                    return new CommonResponse<>(e.getErrorCode(), exceptionMessage);
                })
                .orElse(new CommonResponse<>(e.getErrorCode(), e.getMessage()));
    }

}
