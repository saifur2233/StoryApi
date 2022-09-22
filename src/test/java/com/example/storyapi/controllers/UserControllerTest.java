package com.example.storyapi.controllers;

import com.example.storyapi.Filter.JwtFilter;
import com.example.storyapi.models.Users;
import com.example.storyapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setup(){
        mockMvc= MockMvcBuilders
                .webAppContextSetup(webApplicationContext).addFilter(jwtFilter, "/*").build();
    }

    @Test
    @DisplayName("GET Find one user - Found")
    void testFindById() throws Exception{
        Users mockUser = new Users(1, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
        when(userService.getUserInfo(mockUser.getEmail()))
                .thenReturn(mockUser)
                .thenThrow(new EntityNotFoundException("Error occurred"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{email}","saif55@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Saifur"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("saif55@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("Saifur123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("1798277732"));
    }

//    @Test
//    @DisplayName("GET /api/v1/users - Not Found")
//    void testGetUserNotFound() throws Exception{
//        Users mockUser = new Users(1000, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
//        when(userService.getUserInfo(mockUser.getEmail()))
//                .thenThrow(new EntityNotFoundException("Error occurred"));
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{email}","saif55@gmail.com"))
//                .andExpect(status().isNotFound());
//    }

    @Test
    @DisplayName("GET All user - Found")
    void testFindAllUser() throws Exception{
        Users mockUser1 = new Users(1, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
        Users mockUser2 = new Users(2, "Saif","saif55@gmail.com", "Saifur123","1798277732");
        when(userService.getAllUsers())
                .thenReturn(Arrays.asList(mockUser1, mockUser2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("PUT /api/v1/users/1 - User Update Success")
    void testUserUpdate() throws Exception {
        Users putUser = new Users("Saifur", "saif@gmail.com", "Saifur123", "1798277732");
        Users mockUser = new Users(1, "Saifur", "saif@gmail.com", "Saifur123", "1798277732");

        doReturn(mockUser).when(userService).updateUser(eq(mockUser.getId()), any(Users.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(putUser)))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Saifur"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("saif@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("Saifur123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("1798277732"));
    }

    @Test
    @DisplayName("DELETE user delete - Success")
    void testUserDelete() throws Exception{
        doNothing().when(userService).deleteUser(3);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", 3))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE user delete - Not Found")
    void testUserDeleteNotFound() throws Exception{
        doNothing().when(userService).deleteUser(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", 1))
                .andExpect(status().isNoContent());
    }

    private String asJsonString(final Users putUser) {
        try{
            return objectMapper.writeValueAsString(putUser);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
