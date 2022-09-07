package com.example.storyapi.services;

import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.utils.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtility jwtUtility;
    public Users signUp(Users users){
        return userRepository.save(users);
    }

    public Optional<Users> signIn(Users users){
        Optional<Users> validUser = userRepository.findByEmail(users.getEmail());
        if (validUser.isEmpty()) throw new EntityNotFoundException(Users.class, "Email", users.getEmail());
        if(validUser.get().getPassword().equals(users.getPassword())) {
            final String token  = jwtUtility.generateToken(validUser.get());

            return validUser;
        }
        return Optional.empty();

    }

}
