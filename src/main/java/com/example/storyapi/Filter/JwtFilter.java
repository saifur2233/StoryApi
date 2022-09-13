package com.example.storyapi.Filter;

import com.example.storyapi.exceptions.AccessDeniedException;
import com.example.storyapi.exceptions.JWTException;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.services.UserDetailsServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsServiceInfo userDetailsServiceInfo;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if(authorization != null && authorization.startsWith("Bearer")){
            token = authorization.substring(7);
            try{
                email = jwtUtility.getEmailFromToken(token);
            }
            catch (Exception e){
                throw new JWTException(e.getMessage());
            }
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername(email);
            if (jwtUtility.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
