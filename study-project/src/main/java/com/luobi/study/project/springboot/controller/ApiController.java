package com.luobi.study.project.springboot.controller;

import com.luobi.study.project.common.model.CommonResponse;
import com.luobi.study.project.common.web.LoggingRestTemplate;
import com.luobi.study.project.common.web.exception.APIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-test")
public class ApiController {

    private final LoggingRestTemplate loggingRestTemplate;

    @GetMapping("/baidu")
    public CommonResponse<String> testCallBaiduApi() {
        return CommonResponse.success(callBaiduApi());
    }

    public String callBaiduApi() {
        String url = "https://www.baidu.com/sugrec";
        return Optional.of(
                        loggingRestTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                new HttpEntity<>(new HttpHeaders()),
                                String.class
                        ))
                .filter(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                .map(ResponseEntity::getBody)
                .orElseThrow(APIException::new);
    }

}
