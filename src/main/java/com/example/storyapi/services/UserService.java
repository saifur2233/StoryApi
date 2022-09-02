package com.example.storyapi.services;

import com.example.storyapi.models.User;
import com.example.storyapi.repositories.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(int id){
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(int id, User user){
        Optional<User> userObj =  userRepository.findById(id);
        if (userObj.isEmpty()){
            return Optional.empty();
        }
        userObj.get().setName(user.getName());
        userObj.get().setEmail(user.getEmail());
        userObj.get().setPassword(user.getPassword());
        userObj.get().setPhoneNumber(user.getPhoneNumber());
        userRepository.save(userObj.get());
        return userObj;
    }

    public Optional<User> deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
            return user;
        }
        return Optional.empty();
    }
}
