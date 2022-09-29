package com.example.storyapi.controllers;

import com.example.storyapi.exceptions.PasswordNotMatchException;
import com.example.storyapi.models.Users;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.services.AuthService;
import com.example.storyapi.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtility jwtUtility;

    @Test
    @DisplayName("POST user Sign up - Success")
    void testSignUp() throws Exception{
        Users mockUser = new Users("Saifur","saif77@gmail.com", "Saifur123", "1798277732");
        Users signupUser = new Users(1,"Saifur","saif77@gmail.com", "Saifur123", "1798277732");
        String token = "sdjkjksdjk";
        doReturn(signupUser).when(authService).signUp(mockUser);
        doReturn(token).when(jwtService).authenticate(mockUser.getEmail());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockUser)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST user Sign in - Success")
    void testSignIn() throws Exception{
        Users mockUser = new Users("saif77@gmail.com", "Saifur123");
        Users signinUser = new Users(1,"Saifur","saif77@gmail.com", "Saifur123", "1798277732");
        String token = "sdjkjksdjk";
        doReturn(Optional.of(signinUser)).when(authService).signIn(mockUser);
        doReturn(token).when(jwtService).authenticate(mockUser.getEmail());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockUser)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST user Sign in - Password Not Match")
    void testSignInPassNotMatch() throws Exception{
        Users mockUser = new Users("saif77889911@gmail.com", "123");
        when(authService.signIn(mockUser)).thenThrow(new PasswordNotMatchException(AuthControllerTest.class," ","Password"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockUser)))
                .andExpect(status().isBadRequest());
    }

    private String asJsonString(final Users postUser) {
        try{
            return objectMapper.writeValueAsString(postUser);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
