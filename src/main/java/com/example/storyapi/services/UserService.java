package com.example.storyapi.services;

import com.example.storyapi.exceptions.DuplicateEmailException;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.UserRouteProtection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRouteProtection userRouteProtection;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUser(Integer id){
        Optional<Users> user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
        return user.get();
    }

    public Users updateUser(Integer id, Users users){
        userRouteProtection.checkUserValidation(id);
        Optional<Users> userObj =  userRepository.findById(id);
        if (userObj.isEmpty()){
            throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
        }
        Optional<Users> existEmail = userRepository.findByEmail(users.getEmail());
        if (existEmail.isPresent() && !(id.equals(existEmail.get().getId()))){
            throw new DuplicateEmailException(Users.class, " Email ", existEmail.get().getEmail());
        }
        setUserProperties(userObj.get(), users);
        userRepository.save(userObj.get());
        return userObj.get();

    }

    protected void setUserProperties(Users currentUsers, Users users) {
        currentUsers.setName(users.getName());
        currentUsers.setEmail(users.getEmail());
        currentUsers.setPassword(passwordEncoder.encode(users.getPassword()));
        currentUsers.setPhoneNumber(users.getPhoneNumber());
    }

    public void deleteUser(Integer id){
        userRouteProtection.checkUserValidation(id);
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }
    }
}
