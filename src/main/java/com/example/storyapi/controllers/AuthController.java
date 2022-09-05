package com.example.storyapi.controllers;

import com.example.storyapi.models.User;
import com.example.storyapi.services.AuthService;
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

    @PostMapping(value = "/signup")
    public ResponseEntity<User> signUp(@RequestBody User user){
        User signupUser = authService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupUser);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<? extends Object> signIn(@RequestBody User user){
        Optional<User> loggedUser = authService.signIn(user);
        if (loggedUser.isEmpty()) {
            return new ResponseEntity<>("Password didn't not match", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(loggedUser);
    }
}
