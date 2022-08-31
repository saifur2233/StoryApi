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

    public Optional<User> getUserByEamil(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> updateUser(int id, User user){
        Optional<User> userObj =  userRepository.findById(id);
        if (userObj.isEmpty()){
            return Optional.empty();
        }
        if (!Strings.isBlank(user.getName())) {
            userObj.get().setName(user.getName());
        }
        if (!Strings.isBlank(user.getEmail())) {
            userObj.get().setEmail(user.getEmail());
        }
        if (!Strings.isBlank(user.getPassword())) {
            userObj.get().setPassword(user.getPassword());
        }
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
