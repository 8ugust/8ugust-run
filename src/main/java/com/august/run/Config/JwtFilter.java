package com.august.run.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    
    private final TokenProvider tokenProvider;
    private String AUTHORIZATION_KEY = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = resolveToken(request);

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public String resolveToken(HttpServletRequest request) {
        String jwt = request.getHeader(AUTHORIZATION_KEY);

        if (StringUtils.hasText(jwt) && jwt.startsWith("BEARER ")) {
            return jwt.substring(7);
        }

        return null;
    }

}
