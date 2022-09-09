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
        return userRepository.findAll();
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
        setUserProperties(userObj.get(), user);
        userRepository.save(userObj.get());
        return userObj.get();
    }

    protected void setUserProperties(User currentUser, User user) {
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setPhoneNumber(user.getPhoneNumber());
    }

    public void deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }
        throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
    }

    public User loadUserByEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) throw new EntityNotFoundException(User.class, "email", email);
        return findUser.get();
    }
}
