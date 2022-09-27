package com.example.storyapi.utils;

import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreateStoryRouteProtectionTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private CreateStoryRouteProtection createStoryRouteProtection;

    @Test
    void testroutePreotection(){
        String email = "saif@gmail.com";
        doReturn(email).when(authenticationProvider).secured();
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail("saif@gmail.com");

        createStoryRouteProtection.checkUserValidation();
    }
}
