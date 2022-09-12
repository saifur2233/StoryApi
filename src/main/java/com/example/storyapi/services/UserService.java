package com.example.storyapi.services;

import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUser(int id){
        Optional<Users> user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
        return user.get();
    }

    public Users updateUser(int id, Users users){
        Optional<Users> userObj =  userRepository.findById(id);
        if (userObj.isEmpty()){
            throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
        }
        setUserProperties(userObj.get(), users);
        userRepository.save(userObj.get());
        return userObj.get();
    }

    protected void setUserProperties(Users currentUsers, Users users) {
        currentUsers.setName(users.getName());
        currentUsers.setEmail(users.getEmail());
        currentUsers.setPassword(users.getPassword());
        currentUsers.setPhoneNumber(users.getPhoneNumber());
    }

    public void deleteUser(int id){
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }
        throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
    }

    public Users loadUserByEmail(String email) {
        Optional<Users> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) throw new EntityNotFoundException(Users.class, "email", email);
        return findUser.get();
    }
}
