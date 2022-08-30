package com.example.storyapi.services;

import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUsers(){
        List<Users> users = new ArrayList<>();
        userRepository.findAll().forEach(users :: add);
        return users;
    }
    public void addUser(Users users){
        System.out.println(users.getEmail()+" "+users.getPassword());
        userRepository.save(users);
    }

    public Optional<Users> getUser(int id){
        return userRepository.findById(id);
    }

    public String updateUser(String email, Users users){
        if (userRepository.existsByEmail(email)){
            Users users1 =  userRepository.findByEmail(email);
            int userId = users1.getId();
            userRepository.findById(userId)
                    .map(u -> {
                        u.setEmail(users.getEmail());
                        u.setPassword(users.getPassword());
                        return userRepository.save(u);
                    });
            return "updated";
        }
        return "Email not Found";
    }

    public void deleteUser(Users users){
        userRepository.deleteById(users.getId());
    }
}
