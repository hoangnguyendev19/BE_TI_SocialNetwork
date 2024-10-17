package com.tma.demo.configuration.security.filter;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.constant.AttributeConstant;
import com.tma.demo.constant.CommonConstant;
import com.tma.demo.entity.Token;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.TokenRepository;
import com.tma.demo.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * JwtAuthFilter
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AttributeConstant.HEADER_AUTHORIZATION);
        String jwt;
        String userEmail = null;
        if (authHeader == null || !authHeader.startsWith(CommonConstant.PREFIX_TOKEN)) {
            filterChain.doFilter(request, response);
//            sendError(response,ErrorCode.UNAUTHENTICATED);
            return;
        }
        jwt = authHeader.substring(7);

        try {
            userEmail = jwtService.extractEmail(jwt);
        } catch (Exception e) {
            sendError(response, ErrorCode.TOKEN_INVALID);
        }

        if ( SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            Optional<Token> token = tokenRepository.findByAccessToken(jwt);
            if (token.isEmpty()) {
                sendError(response, ErrorCode.TOKEN_INVALID);
            } else {
                if (jwtService.isExpired(jwt)) {
                    sendError(response, ErrorCode.TOKEN_EXPIRED);
                }
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
//                collect details about current web (remote address, session ID, etc) from http request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private static void sendError(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getCode());
        response.setContentType(CommonConstant.JSON_CONTENT_TYPE);
        response.getWriter().write(errorCode.getMessage());
        response.getWriter().flush();
    }


}