
package org.komea.product.web.rest.api;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);
    
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
    
        LOGGER.debug("je passe dans le cors filter =============================");
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, UPDATE, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-HTTP-Method-Override");
            response.addHeader("Access-Control-Max-Age", "1800");// 30 min
        }
        filterChain.doFilter(request, response);
    }
}
