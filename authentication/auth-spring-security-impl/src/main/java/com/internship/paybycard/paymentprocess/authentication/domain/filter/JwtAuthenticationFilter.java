package com.internship.paybycard.paymentprocess.authentication.domain.filter;

import com.internship.paybycard.paymentprocess.authentication.core.domain.service.TokenService;
import com.internship.paybycard.paymentprocess.authentication.domain.service.SysUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final SysUserDetailsService sysUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String jwt = authHeader.substring(7);
            final String username = tokenService.extractUsername(jwt);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("validating jwt token for user: \"{}\"", username);
                if (tokenService.validateToken(jwt)) {
                    log.info("storing user details in security context for user: \"{}\"", username);
                    UserDetails userDetails = sysUserDetailsService.userDetailsService().loadUserByUsername(username);
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                    securityContext.setAuthentication(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    log.error("invalid jwt token for request id: {}", request.getRequestId());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid or expired jwt");
                    return;
                }
            }
        }else {
            log.error("invalid auth header, request id={}",request.getRequestId());
        }
        filterChain.doFilter(request, response);
    }
}