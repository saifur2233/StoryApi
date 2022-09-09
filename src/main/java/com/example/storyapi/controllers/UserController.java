package com.example.storyapi.controllers;

import com.example.storyapi.models.User;
import com.example.storyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers(){
        Iterable<User> user = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user){
        User updated = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
