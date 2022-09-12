package com.example.storyapi.utils;

import com.example.storyapi.exceptions.AccessDeniedException;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Story;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProtectCreateStoryApi {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetUserEmail protectStoryApi;

    public Integer checkUserValidation(){
        String userEmail = protectStoryApi.secured();
        Optional<Users> users = userRepository.findByEmail(userEmail);

        if(users.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(users.get().getId()));
        return users.get().getId();
    }
}
