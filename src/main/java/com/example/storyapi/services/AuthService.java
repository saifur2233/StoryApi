package com.example.storyapi.services;

import com.example.storyapi.models.User;
import com.example.storyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User signUp(User user){
        return userRepository.save(user);
    }

    public Optional<User> signIn(User user){
        Optional<User> validUser = userRepository.findByEmail(user.getEmail());
        if (validUser.isEmpty()) return Optional.empty();
        if(validUser.get().getPassword().equals(user.getPassword())) return validUser;
        return Optional.empty();
    }
}
