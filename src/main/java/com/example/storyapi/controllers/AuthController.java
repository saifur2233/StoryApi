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
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<Optional<User>> signIn(@RequestBody User user){
        Optional<User> loggedUser = authService.signIn(user);
        if (loggedUser.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.OK).body(loggedUser);
    }
}
