package com.example.storyapi.controllers;

import com.example.storyapi.models.Users;
import com.example.storyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody Users users){
        userService.addUser(users);
    }

    @GetMapping(value = "/users/{id}")
    public Optional<Users> getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PutMapping(value = "/users/{email}")
    public String updateUser(@PathVariable String email, @RequestBody Users users){
         return userService.updateUser(email, users);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users")
    public void deleteUser(@RequestBody Users users){
        userService.deleteUser(users);
    }

}
