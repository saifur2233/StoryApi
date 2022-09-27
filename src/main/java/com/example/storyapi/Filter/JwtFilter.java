package com.example.storyapi.Filter;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsServiceInfo userDetailsServiceInfo;

    private static final String COOKIE_NAME = "lexus";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,PUT,PATCH,POST,DELETE");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Accept, X-Requested-With, remember-me");

        String token = null;
        String email = null;

        Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies())
                .orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();
        if(cookieAuth.isPresent()){
            token = cookieAuth.get().getValue();
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
