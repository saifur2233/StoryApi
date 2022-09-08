package com.example.storyapi.services;

import com.example.storyapi.models.User;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.utils.EntityNotFoundException;
import com.example.storyapi.utils.InvalidPropertiesFormatException;
import com.example.storyapi.utils.PasswordEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private PasswordEncrypt passwordEncrypt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtility jwtUtility;
    public User signUp(User user){

        if(PasswordEncrypt.isValid(user.getPassword())){
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(newUser.getPassword());
            return userRepository.save(newUser);
        }
        throw new InvalidPropertiesFormatException(User.class,"Password not found", user.getPassword());
    }

    public Optional<User> signIn(User user){
        Optional<User> validUser = userRepository.findByEmail(user.getEmail());
        if (validUser.isEmpty()) throw new EntityNotFoundException(User.class, "Email", user.getEmail());
        if (passwordEncoder.matches(user.getPassword(),validUser.get().getPassword())){
            return validUser;
        }
        return Optional.empty();

    }

}
