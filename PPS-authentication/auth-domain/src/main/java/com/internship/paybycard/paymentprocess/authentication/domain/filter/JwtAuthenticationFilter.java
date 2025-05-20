package com.internship.paybycard.paymentprocess.authentication.domain.filter;

import com.internship.paybycard.paymentprocess.authentication.core.domain.service.JwtService;
import com.internship.paybycard.paymentprocess.authentication.domain.service.security.SysUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SysUserDetailsService sysUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = authHeader.substring(7);
        final String username= jwtService.extractUsername(jwt);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            //validating token & updating security context
            if (jwtService.validateToken(jwt)) {
                UserDetails userDetails= sysUserDetailsService.userDetailsService().loadUserByUsername(username);
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }
}