package com.example.storyapi.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthenticationFacadeTest {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Test
    void testAuthenticationFacade(){
        authenticationFacade.getAuthentication();
    }
}
