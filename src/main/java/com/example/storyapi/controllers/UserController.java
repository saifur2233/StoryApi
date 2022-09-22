package com.example.storyapi.controllers;

import com.example.storyapi.models.Users;
import com.example.storyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${apiPrefix}"+"/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Users>> getAllUsers(){
        Iterable<Users> user = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<Users> getUser(@PathVariable Integer id) {
//        Users users = userService.getUser(id);
//        return ResponseEntity.status(HttpStatus.OK).body(users);
//    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<Users> getUserInfo(@PathVariable String email) {
        Users users = userService.getUserInfo(email);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users users){
        Users updated = userService.updateUser(id, users);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
