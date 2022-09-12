package com.example.storyapi.controllers;

import com.example.storyapi.models.JwtResponse;
import com.example.storyapi.models.Users;
import com.example.storyapi.services.AuthService;
import com.example.storyapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "${apiPrefix}")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@RequestBody Users users){
        Users signupUsers = authService.signUp(users);
        JwtResponse token = jwtService.authenticate(signupUsers.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signIn(@RequestBody Users users){
        Optional<Users> loggedUser = authService.signIn(users);
        if (loggedUser.isEmpty()) {
            return new ResponseEntity<>("Password didn't not match", HttpStatus.UNAUTHORIZED);
        }
        JwtResponse token = jwtService.authenticate(loggedUser.get().getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
