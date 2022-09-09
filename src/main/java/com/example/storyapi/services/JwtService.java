package com.example.storyapi.services;

import com.example.storyapi.models.JwtResponse;
import com.example.storyapi.models.User;
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

    public JwtResponse authenticate(@RequestBody User users) {
        final User user = userService.loadUserByEmail(users.getEmail());
        final String token  = jwtUtility.generateToken(user);
        return new JwtResponse(token);
    }
}
