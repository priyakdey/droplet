package com.priyakdey.droplet.api.config.filter;

import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Priyak Dey
 */
@Component
public class CorrelationIdResponseHeaderFilter extends OncePerRequestFilter {
    private final Tracer tracer;

    public CorrelationIdResponseHeaderFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String traceId = Optional.ofNullable(tracer.currentTraceContext().context())
                .map(TraceContext::traceId)
                .orElse("");
        response.setHeader("X-Correlation-Id", traceId);
        filterChain.doFilter(request, response);
    }
}
