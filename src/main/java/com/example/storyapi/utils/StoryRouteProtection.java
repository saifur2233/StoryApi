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
public class StoryRouteProtection {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetUserEmail protectStoryApi;

    public void checkUserValidation(Integer blogtId){
        String userEmail = protectStoryApi.secured();
        Optional<Users> users = userRepository.findByEmail(userEmail);

        if (users.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(blogtId));
        if(!(blogtId.equals(users.get().getId()))) throw new AccessDeniedException("id", String.valueOf(users.get().getId()));
    }
}
