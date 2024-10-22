package com.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.service.JWTService;
import java.util.Collections;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class TokenFilter implements Filter {
    @Autowired
    private JWTService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURL().toString();

        if (url.contains("/public/")) {
            chain.doFilter(request, response);
            return;
        }

        String token = req.getHeader("Authorization");
        System.out.println("Token from filter - 1 : " + token);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove 'Bearer ' prefix
            System.out.println("Token from filter - 2 : " + token);

            if (jwtService.validateToken(token)) {
                String email = jwtService.validateTokeAndGetEmail(token);
                String role = jwtService.validateTokenAndGetRole(token);

                // Create authentication object
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(email, null, 
                    Collections.singletonList(new SimpleGrantedAuthority(role)));
                
                // Set the authentication in the context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("Email: " + email);
                System.out.println("Role: " + role);

                chain.doFilter(request, response);
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Invalid or expired token");
            }
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
        }
    }
}
