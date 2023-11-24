package com.luobi.study.project.common.web.config;

import com.luobi.study.project.common.web.LoggingRestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(60000);
        factory.setReadTimeout(180000);
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public LoggingRestTemplate loggingRestTemplateIgnoreStatus() {
        return new LoggingRestTemplate(clientHttpRequestFactory());
    }

}
