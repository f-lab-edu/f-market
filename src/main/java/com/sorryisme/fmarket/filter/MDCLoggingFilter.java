package com.sorryisme.fmarket.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCLoggingFilter implements Filter {

    private static final String MDC_REQUEST_UUID = "request_UUID";
    private static final String MDC_URI = "uri";
    private static final String MDC_METHOD = "method";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        MDC.put(MDC_REQUEST_UUID, UUID.randomUUID().toString());
        MDC.put(MDC_URI, wrappedRequest.getRequestURI());
        MDC.put(MDC_METHOD, wrappedRequest.getMethod());
        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long processingTime = System.currentTimeMillis() - startTime;
            loggingRequestResponse(wrappedRequest, wrappedResponse, processingTime);
            wrappedResponse.copyBodyToResponse();
            MDC.clear();
        }
    }

    private void loggingRequestResponse(ContentCachingRequestWrapper wrappedRequest, ContentCachingResponseWrapper wrappedResponse, long elapsedTime) {
        log.info(toString(wrappedRequest, wrappedResponse, elapsedTime));
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] body = requestWrapper.getContentAsByteArray();
        return new String(body, StandardCharsets.UTF_8);
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        byte[] body = responseWrapper.getContentAsByteArray();
        return new String(body, StandardCharsets.UTF_8);
    }

    private String toString(ContentCachingRequestWrapper wrappedRequest, ContentCachingResponseWrapper wrappedResponse, long elapsedTime) {
        return "|\n" +
                String.format(">>[USER_AGENT]: %s\n", wrappedRequest.getHeader("User-Agent")) +
                String.format(">>[REFERER]: %s\n", wrappedRequest.getHeader("Referer")) +
                String.format(">>[ORIGIN]: %s\n", wrappedRequest.getHeader("Origin")) +
                String.format(">>[Idempotency-Key]: %s\n", wrappedRequest.getHeader("Idempotency-Key")) +
                String.format(">>[REMOTE_ADDR]: %s\n", wrappedRequest.getRemoteAddr()) +
                String.format(">>[REMOTE_HOST]: %s\n", wrappedRequest.getRemoteHost()) +
                String.format(">>[REQUEST_BODY]: %s\n", getRequestBody(wrappedRequest)) +
                String.format(">>[STATUS]: %s\n", wrappedResponse.getStatus()) +
                String.format(">>[ProcessingTime]: %d\n", elapsedTime) +
                String.format(">>[RESPONSE_BODY]: %s", getResponseBody(wrappedResponse));
    }

}

