package com.example.storyapi.utils;

import com.example.storyapi.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateSetProperties {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void setUserProperties(Users currentUsers, Users users) {
        currentUsers.setName(users.getName());
        currentUsers.setPassword(passwordEncoder.encode(users.getPassword()));
        currentUsers.setPhoneNumber(users.getPhoneNumber());
    }
}
