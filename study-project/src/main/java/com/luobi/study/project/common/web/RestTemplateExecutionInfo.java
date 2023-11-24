package com.luobi.study.project.common.web;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RestTemplateExecutionInfo {

    private RequestInfo requestInfo;
    private ResponseInfo responseInfo;
    private String errorStackTrace;

    public String toExceptionMessage() {
        String uri = Optional.ofNullable(requestInfo).map(RequestInfo::getUri).orElse("");
        String method = Optional.ofNullable(requestInfo).map(RequestInfo::getMethod).orElse("");
        String requestHeaders = Optional.ofNullable(requestInfo).map(RequestInfo::getRequestHeaders).orElse("");
        String requestBody = Optional.ofNullable(requestInfo).map(RequestInfo::getRequestBody).orElse("");
        String responseStatus = Optional.ofNullable(responseInfo).map(ResponseInfo::getResponseStatus).orElse("");
        String responseBody = Optional.ofNullable(responseInfo).map(ResponseInfo::getResponseBody).orElse("");
        return String.format(
                "Exception occurred while calling uri : %s, method : %s, headers %s, body %s, and responded with status : %s, body : %s",
                uri, method, requestHeaders, requestBody, responseStatus, responseBody
        );
    }

    public String toLogMessage() {
        String stackTrace = nonNull(errorStackTrace) ? errorStackTrace : StringUtils.EMPTY;
        return toExceptionMessage() + ", stackTrace : " + stackTrace;
    }

    public String toClassicExceptionMessage() {
        String uri = Optional.ofNullable(requestInfo).map(RequestInfo::getUri).orElse("");
        String method = Optional.ofNullable(requestInfo).map(RequestInfo::getMethod).orElse("");
        String requestHeaders = Optional.ofNullable(requestInfo).map(RequestInfo::getRequestHeaders).orElse("");
        String requestBody = Optional.ofNullable(requestInfo).map(RequestInfo::getRequestBody).orElse("");
        String responseStatus = Optional.ofNullable(responseInfo).map(ResponseInfo::getResponseStatus).orElse("");
        String responseBody = Optional.ofNullable(responseInfo).map(ResponseInfo::getResponseBody).orElse("");
        return String.format(
                "Request URL: %s\nRequest Method: %s\nRequest Headers: %s\nRequest Body: %s\nResponse status: %s\nResponse Body: %s",
                uri, method, requestHeaders, requestBody, responseStatus, responseBody);
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {
        private String uri;
        private String method;
        private String requestHeaders;
        private String requestBody;
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseInfo {
        private String responseStatus;
        private String responseBody;
    }

}
