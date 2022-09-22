package com.example.storyapi.services;

import com.example.storyapi.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private UserDetailsServiceInfo userDetailsServiceInfo;

    public String authenticate(String email) {
        final UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername(email);
        final String token  = jwtUtility.generateToken(userDetails);
        return token;
    }
}
