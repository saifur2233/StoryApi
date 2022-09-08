package com.example.storyapi.services;

import com.example.storyapi.models.User;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.utils.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtility jwtUtility;
    public User signUp(User user){
        return userRepository.save(user);
    }

    public Optional<User> signIn(User user){
        Optional<User> validUser = userRepository.findByEmail(user.getEmail());
        if (validUser.isEmpty()) throw new EntityNotFoundException(User.class, "Email", user.getEmail());
        if(validUser.get().getPassword().equals(user.getPassword())) {
            final String token  = jwtUtility.generateToken(validUser.get());

            return validUser;
        }
        return Optional.empty();

    }

}
