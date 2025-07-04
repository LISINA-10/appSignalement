package com.ueprojet.appSignalement.authentification;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter { 

    @Autowired 
    private JwtUtil jwtUtil; 
 
    @Autowired 
    private UserDetailsService userDetailsService; 
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException { 
       final String authorizationHeader = request.getHeader("Authorization"); 

       String username = null; 
       String jwt = null; 

       if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { 
           jwt = authorizationHeader.substring(7); 
           username = jwtUtil.extractUsername(jwt); 
       } 

       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
           UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); 

           if (jwtUtil.validateToken(jwt, userDetails.getUsername())) { 
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( 
                       userDetails, null, userDetails.getAuthorities()); 

               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); 
           } 
       } 

       chain.doFilter(request, response); 
   } 
}

