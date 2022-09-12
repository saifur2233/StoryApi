package com.example.storyapi.utils;


import com.example.storyapi.exceptions.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    public String secured(){
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication.isAuthenticated()) return authentication.getName();
        throw new AccessDeniedException("access denied","user");
    }


}
