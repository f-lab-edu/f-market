package com.sorryisme.fmarket.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCLoggingFilter implements Filter {

    private static final String MDC_REQUEST_UUID = "request_UUID";
    private static final String MDC_URI = "uri";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(MDC_REQUEST_UUID, UUID.randomUUID().toString());

        if (servletRequest instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) servletRequest).getRequestURI();
            MDC.put(MDC_URI, uri);
        }

        filterChain.doFilter(servletRequest, servletResponse);
        MDC.clear();
    }
}
