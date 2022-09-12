package com.example.storyapi.services;

import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.exceptions.InvalidPropertiesFormatException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.utils.PasswordEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtility jwtUtility;
    public Users signUp(Users users){
        if(PasswordEncrypt.isValid(users.getPassword())){
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            return userRepository.save(users);
        }
        throw new InvalidPropertiesFormatException(Users.class,"Password not found", users.getPassword());
    }

    public Optional<Users> signIn(Users users){
        Optional<Users> validUser = userRepository.findByEmail(users.getEmail());
        if (validUser.isEmpty()) throw new EntityNotFoundException(Users.class, "Email", users.getEmail());
        if (passwordEncoder.matches(users.getPassword(), validUser.get().getPassword())){
            return validUser;
        }
        return Optional.empty();

    }

}
