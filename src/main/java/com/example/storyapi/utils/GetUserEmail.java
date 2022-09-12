package com.example.storyapi.utils;


import com.example.storyapi.exceptions.AccessDeniedException;
import com.example.storyapi.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GetUserEmail {

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    public String secured(){
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication.isAuthenticated()) return authentication.getName();
        throw new AccessDeniedException("access denied","user");
    }


}
