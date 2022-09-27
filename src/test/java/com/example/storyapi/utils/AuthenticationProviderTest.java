package com.example.storyapi.utils;

import com.example.storyapi.exceptions.AccessDeniedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthenticationProviderTest {
    @MockBean
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Test
    @DisplayName("User authentication test")
    void testUserAuthenticationException(){
        String name= "saif@gmail.com";
        Authentication authentication = authenticationFacade.getAuthentication();
        when(authenticationFacade.getAuthentication())
                .thenThrow(new AccessDeniedException("access denied","user"));

        Exception exception = Assertions.assertThrows(AccessDeniedException.class, () -> authenticationProvider.secured());
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        String expectedMessage = "Access denied access denied user";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
