package com.example.storyapi.services;

import com.example.storyapi.exceptions.DuplicateEmailException;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.PasswordFormatValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @MockBean
    private PasswordFormatValidation passwordFormatValidation;

@Test
@DisplayName("POST USer sign in - Not Found")
void testSignInUserNotFound(){
    Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
    Users signinUser = new Users( "saif12121212@gmail.com", "Saifur123");

    when(userRepository.findByEmail("saif12121212@gmail.com")).thenThrow(new EntityNotFoundException(AuthServiceTest.class," Email ","saif12121212@gmail.com"));

    Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> authService.signIn(signinUser));
    String actualMessage = exception.getMessage();
    //System.out.println(actualMessage);
    String expectedMessage = "AuthServiceTest not found with  Email  saif12121212@gmail.com";
    Assertions.assertTrue(actualMessage.contains(expectedMessage));
}

    @Test
    @DisplayName("POST USer sign Up - User Email Exist")
    void testSignUpUserExist(){
        Users mockUser = new Users( "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        when(userRepository.findByEmail("saif@gmail.com")).thenThrow(new DuplicateEmailException(AuthServiceTest.class, " Email ","saif@gmail.com"));

        Exception exception = Assertions.assertThrows(DuplicateEmailException.class, () -> authService.signUp(mockUser));
        String actualMessage = exception.getMessage();
        String expectedMessage = "AuthServiceTestAlready Exist Duplicate  Email  saif@gmail.com";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("POST user sign up - Success")
    void testUserSignUp(){
    Users postUser = new Users( "Saifur","saif88@gmail.com", "Saifur123", "1798277732");
    Users mockUser = new Users(1, "Saifur","saif88@gmail.com", "Saifur123", "1798277732");
    doReturn(Optional.of(mockUser)).when(userRepository).findByEmail("saif99@gmail.com");
    String encodePassword = "kjksdjjksdj";
        doReturn(encodePassword).when(passwordEncoder).encode(postUser.getPassword());
        doReturn(mockUser).when(userRepository).save(postUser);

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);

        Users user = authService.signUp(postUser);
        verify(userRepository).save(captor.capture());
        //System.out.println("User 1"+ user);
        //System.out.println("user 2"+ captor.getValue());
        Users captureUser = captor.getValue();
        Assertions.assertNotNull(captureUser);
        Assertions.assertEquals(mockUser,user);
    }

    @Test
    @DisplayName("POST user sign In - Success")
    void testUserSignIn(){
        Users postUser = new Users( "saif@gmail.com", "Saifur123");
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail("saif@gmail.com");

        doReturn(true).when(passwordEncoder).matches(postUser.getPassword(), "Saifur123");

        Optional<Users> validUser = authService.signIn(postUser);
        Assertions.assertEquals(mockUser, validUser.get());
    }


}
