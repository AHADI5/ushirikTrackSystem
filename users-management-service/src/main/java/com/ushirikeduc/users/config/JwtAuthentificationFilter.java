package com.ushirikeduc.users.config;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private  final JwtService jwtService ;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        //the header containing the JWTOKEN
        final String authHeader  = request.getHeader("Authorization");
        final String jwt ;
        final String userEmail;
        if (authHeader == null || authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //Extracting the token form the Authentication header
        jwt = authHeader.substring(7) ;

        //Extracting user email from the request
        userEmail = jwtService.extractUsername(jwt) ;




    }
}
