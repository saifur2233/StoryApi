package com.example.storyapi.services;

import com.example.storyapi.models.JwtResponse;
import com.example.storyapi.models.Users;
import com.example.storyapi.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class JwtService {

    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private UserService userService;

    public JwtResponse authenticate(@RequestBody Users users) throws Exception{
        final Users user = userService.loadUserByEmail(users.getEmail());
        final String token  = jwtUtility.generateToken(user);
        System.out.println(token);
        return new JwtResponse(token);
    }
}
