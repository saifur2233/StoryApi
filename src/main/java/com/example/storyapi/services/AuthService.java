package com.example.storyapi.services;

import com.example.storyapi.models.User;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User signUp(User user){
        return userRepository.save(user);
    }

    public User signIn(User user){
        Optional<User> validUser = userRepository.findByEmail(user.getEmail());
        if (validUser.isEmpty()) throw new EntityNotFoundException(User.class, "Email", user.getEmail());
        if(validUser.get().getPassword().equals(user.getPassword())) return validUser.get();
        return null;
    }
}
