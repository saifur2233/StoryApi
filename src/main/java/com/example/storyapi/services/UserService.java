package com.example.storyapi.services;

import com.example.storyapi.models.User;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers(){
        Iterable<User> user = userRepository.findAll();
        if(user == null){
            throw new EntityNotFoundException(User.class);
        }
        return user;
    }

    public User getUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
        return user.get();
    }

    public User updateUser(int id, User user){
        Optional<User> userObj =  userRepository.findById(id);
        if (userObj.isEmpty()){
            throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
        }
        userObj.get().setName(user.getName());
        userObj.get().setEmail(user.getEmail());
        userObj.get().setPassword(user.getPassword());
        userObj.get().setPhoneNumber(user.getPhoneNumber());
        userRepository.save(userObj.get());
        return userObj.get();
    }

    public User deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
            return user.get();
        }
        throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
    }
}
