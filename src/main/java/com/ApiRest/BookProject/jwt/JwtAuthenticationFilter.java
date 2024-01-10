package com.ApiRest.BookProject.jwt;

import com.ApiRest.BookProject.persistence.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // extendemos de esta clase que permite personalizar los filtros y una vez por solicitud http

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

               
        final String token = getTokenFromRequest(request);
        final String username;
        final Role rol;
        
        if(token==null){
            filterChain.doFilter(request, response);
            return;
        }
        
        username= jwtService.getUsernameFromToken(token);
        rol = jwtService.getRoleFromToken(token);
        
        if (username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if(jwtService.isTokenValid(token, userDetails)){
                
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
                
            }
            
        }
        
        
        filterChain.doFilter(request, response);
        
        
        
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {

            return authHeader.substring(7);
        }

        return null;

    }

}

