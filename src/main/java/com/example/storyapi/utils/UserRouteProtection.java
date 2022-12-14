package com.example.storyapi.utils;

import com.example.storyapi.exceptions.AccessDeniedException;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRouteProtection {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    public void checkUserValidation(Integer requestId){
        String userEmail = authenticationProvider.secured();
        Optional<Users> users = userRepository.findByEmail(userEmail);
        if (users.isEmpty()) throw new EntityNotFoundException(Users.class, "id", String.valueOf(requestId));
        Integer userId = users.get().getId();
        if(!(requestId.equals(userId))) throw new AccessDeniedException("id", String.valueOf(requestId));
    }
}
