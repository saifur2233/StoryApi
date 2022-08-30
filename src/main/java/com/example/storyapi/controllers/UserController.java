package com.example.storyapi.controllers;

import com.example.storyapi.models.User;
import com.example.storyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers(){
        Iterable<User> user = userService.getAllUsers();
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }


    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping(value = "/{email}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable String email, @RequestBody User user){
        Optional<User> updated = userService.updateUser(email, user);
        if (updated.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Optional<User>> deleteUser(@PathVariable int id){
        Optional<User> user = userService.deleteUser(id);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
