package com.gdsc_solutionchallenge.backend.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Perform the JWT authentication filter logic.
     *
     * @param request  The servlet request.
     * @param response The servlet response.
     * @param chain    The filter chain.
     * @throws IOException      if an I/O error occurs.
     * @throws ServletException if a servlet-specific error occurs.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. Extract JWT token from the Request Header
        String token = resolveToken((HttpServletRequest) request);

        // 2. Validate the token using validateToken
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // If the token is valid, retrieve the Authentication object from the token and store it in the SecurityContext
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * Resolve token information from the Request Header.
     *
     * @param request The HttpServletRequest from which to extract the token.
     * @return The extracted token or null if not found.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
