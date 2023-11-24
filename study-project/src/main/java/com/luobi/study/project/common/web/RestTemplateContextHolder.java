package com.luobi.study.project.common.web;

import com.luobi.study.project.common.web.filter.RestTemplateContextFilter;

import java.util.Optional;

import static java.util.Objects.isNull;


public class RestTemplateContextHolder {

    private final static InheritableThreadLocal<RestTemplateExecutionInfo> threadLocal = new InheritableThreadLocal<>();

    private RestTemplateContextHolder() {
        // do not instantiate
    }

    /**
     * Set LoggingRestTemplate's request information to current thread context
     *
     * @param requestInfo
     * @see LoggingRestTemplate ::traceRequest
     */
    public static void setRequestInfo(RestTemplateExecutionInfo.RequestInfo requestInfo) {
        RestTemplateExecutionInfo currentExecutionInfo = threadLocal.get();
        if (isNull(currentExecutionInfo)) {
            currentExecutionInfo = new RestTemplateExecutionInfo();
        }
        currentExecutionInfo.setRequestInfo(requestInfo);
        threadLocal.set(currentExecutionInfo);
    }

    /**
     * Set LoggingRestTemplate's response information to current thread context
     *
     * @param responseInfo
     * @see LoggingRestTemplate::traceResponse
     */
    public static void setResponseInfo(RestTemplateExecutionInfo.ResponseInfo responseInfo) {
        RestTemplateExecutionInfo currentExecutionInfo = threadLocal.get();
        if (isNull(currentExecutionInfo)) {
            currentExecutionInfo = new RestTemplateExecutionInfo();
        }
        currentExecutionInfo.setResponseInfo(responseInfo);
        threadLocal.set(currentExecutionInfo);
    }

    /**
     * Set LoggingRestTemplate's error stacktrace to current thread context
     *
     * @param errorStackTrace
     * @see LoggingRestTemplate::intercept
     */
    public static void setErrorStackTrace(String errorStackTrace) {
        RestTemplateExecutionInfo currentExecutionInfo = threadLocal.get();
        if (isNull(currentExecutionInfo)) {
            currentExecutionInfo = new RestTemplateExecutionInfo();
        }
        currentExecutionInfo.setErrorStackTrace(errorStackTrace);
        threadLocal.set(currentExecutionInfo);
    }

    /**
     * Clear current context
     *
     * @see RestTemplateContextFilter ::doFilterInternal
     */
    public static void clear() {
        threadLocal.remove();
    }

    public static Optional<RestTemplateExecutionInfo> getRestTemplateExecutionInfo() {
        return Optional.ofNullable(threadLocal.get());
    }

}
