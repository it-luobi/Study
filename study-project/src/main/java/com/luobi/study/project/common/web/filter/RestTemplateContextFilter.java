package com.luobi.study.project.common.web.filter;

import com.luobi.study.project.common.web.RestTemplateContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestTemplateContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("Clearing RestTemplate execution information context holder...");
        RestTemplateContextHolder.clear();
        filterChain.doFilter(request, response);
    }

}
