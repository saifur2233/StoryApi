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
public class ProtectStoryApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetUserEmail protectStoryApi;

    public void checkUserValidation(Integer requestId){
        String userEmail = protectStoryApi.secured();
        Optional<Users> users = userRepository.findByEmail(userEmail);

        if (users.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(requestId));
        if(!(requestId.equals(users.get().getId()))) throw new AccessDeniedException(Story.class, "id", String.valueOf(requestId));
    }
}
