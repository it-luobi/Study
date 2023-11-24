package com.luobi.study.project.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LoggingRestTemplate extends RestTemplate {

    public LoggingRestTemplate() {
        super();
        initialize();
    }

    public LoggingRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        initialize();
    }

    private void initialize() {
        // Add interceptors
        final List<ClientHttpRequestInterceptor> interceptorList = getInterceptors();
        interceptorList.add(new CommonAdminHeaderInterceptor());
        interceptorList.add(new LoggingClientHttpRequestInterceptor());

        // Set message converters
        List<HttpMessageConverter<?>> messageConverters = getMessageConverters();
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }

        // Set error handlers
        this.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
                switch (statusCode.series()) {
                    case CLIENT_ERROR:
                    case SERVER_ERROR:
                        break;
                    default:
                        throw new UnknownHttpStatusCodeException(statusCode.value(), response.getStatusText(),
                                response.getHeaders(), getResponseBody(response), getCharset(response));
                }
            }
        });
    }

    private static class CommonAdminHeaderInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            return execution.execute(request, body);
        }

    }

    @Slf4j
    private static class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            log.info("==================== RestTemplate Log Begin ====================");

            traceRequest(request, body);
            try {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                ClientHttpResponse tmp = execution.execute(request, body);
                stopWatch.stop();
                ClientHttpResponse response = new BufferingClientHttpResponseWrapper(tmp);
                traceResponse(request, response, stopWatch);
                return response;
            } catch (IOException e) {
                RestTemplateContextHolder.setErrorStackTrace(e.toString());
                log.error("Error Occurs While Executing: " + e.getMessage(), e);
                throw e;
            } finally {
                log.info("==================== RestTemplate Log End ====================");
            }
        }

        private void traceRequest(HttpRequest request, byte[] body) {
            String uri = request.getURI().toString();
            String method = Optional.ofNullable(request.getMethod()).map(Objects::toString).orElse("");
            String headers = request.getHeaders().toString();
            String requestBody;
            if (headers.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                requestBody = "multipart-form_data body (file)";
            } else {
                requestBody = new String(body, StandardCharsets.UTF_8);
            }

            RestTemplateContextHolder.setRequestInfo(RestTemplateExecutionInfo.RequestInfo.builder()
                    .uri(uri)
                    .method(method)
                    .requestHeaders(headers)
                    .requestBody(requestBody)
                    .build()
            );

            String requestInfo = "========== RestTemplate Request Begin ==========\n"
                    + "URI : " + uri + "\n"
                    + "Method : " + method + "\n"
                    + "Headers : " + headers + "\n"
                    + "Request Body : " + requestBody + "\n"
                    + "========== RestTemplate Request End =========\n";
            log.info(requestInfo);
        }

        private void traceResponse(HttpRequest request, ClientHttpResponse response, StopWatch stopWatch) throws
                IOException {
            StringBuilder responseBody = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
                String line = bufferedReader.readLine();
                while (line != null) {
                    responseBody.append(line);
                    line = bufferedReader.readLine();
                }
            }

            String responseStatus = response.getStatusCode().toString();
            String responseBodyString = isStringMediaType(response) ? responseBody.toString() : "";

            RestTemplateContextHolder.setResponseInfo(RestTemplateExecutionInfo.ResponseInfo.builder()
                    .responseStatus(responseStatus)
                    .responseBody(responseBodyString)
                    .build()
            );

            String responseInfo = "========== RestTemplate Response Begin ==========\n"
                    + "Request URI : " + request.getURI() + "\n"
                    + "Response	Status : " + responseStatus + "\n"
                    + "Response Body : " + responseBodyString + "\n"
                    + "========== RestTemplate Response End, Cost: " + stopWatch.getTotalTimeMillis() + " ms ==========\n";
            log.info(responseInfo);
        }

        private static boolean isStringMediaType(ClientHttpResponse response) {
            MediaType contentType = response.getHeaders().getContentType();
            return MediaType.APPLICATION_JSON.isCompatibleWith(contentType)
                    || MediaType.APPLICATION_XML.includes(contentType)
                    || MediaType.APPLICATION_XHTML_XML.includes(contentType)
                    || MediaType.TEXT_PLAIN.includes(contentType);
        }

        // used to solve close stream
        private static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

            private final ClientHttpResponse response;

            @Nullable
            private byte[] body;

            BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
                this.response = response;
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return this.response.getStatusCode();
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.response.getRawStatusCode();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.response.getStatusText();
            }

            @Override
            public HttpHeaders getHeaders() {
                return this.response.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                if (this.body == null) {
                    this.body = StreamUtils.copyToByteArray(this.response.getBody());
                }
                return new ByteArrayInputStream(this.body);
            }

            @Override
            public void close() {
                this.response.close();
            }

        }

    }

}
