package com.example.storyapi.controllers;

import com.example.storyapi.models.JwtResponse;
import com.example.storyapi.models.User;
import com.example.storyapi.services.AuthService;
import com.example.storyapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/signup")
    public ResponseEntity<? extends Object> signUp(@RequestBody User user) throws Exception {
        User signupUser = authService.signUp(user);
        JwtResponse token = jwtService.authenticate(signupUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<? extends Object> signIn(@RequestBody User user) throws Exception{
        Optional<User> loggedUser = authService.signIn(user);
        JwtResponse token = jwtService.authenticate(loggedUser.get());
        if (loggedUser.isEmpty()) {
            return new ResponseEntity<>("Password didn't not match", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
