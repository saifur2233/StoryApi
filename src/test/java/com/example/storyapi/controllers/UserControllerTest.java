package com.example.storyapi.controllers;

import com.example.storyapi.models.Users;
import com.example.storyapi.services.UserService;
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

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;



    @Test
    @DisplayName("GET Find one user - Found")
    void testFindById() throws Exception{
        Users mockUser = new Users(1, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
        when(userService.getUser(mockUser.getId()))
                .thenReturn(mockUser)
                .thenThrow(new EntityNotFoundException("Error occurred"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Saifur"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("saif55@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("Saifur123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("1798277732"));
    }

    @Test
    @DisplayName("GET /api/v1/users - Not Found")
    void testGetUserNotFound() throws Exception{
        Users mockUser = new Users(1000, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
        when(userService.getUser(mockUser.getId()))
                .thenThrow(new EntityNotFoundException("Error occurred"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}",1))
                .andExpect(status().isOk());
    }

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

//    @Test
//    @DisplayName("PUT /api/v1/users/1 - User Update Success")
//    void testUserUpdate() throws Exception{
//        Users putUser = new Users(1,"Saifur","saif55@gmail.com", "Saifur123","1798277732");
//        Users mockUser = new Users(1, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
//
//        doReturn(mockUser).when(userService).updateUser(putUser.getId(), putUser);
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}",1)
//                .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(putUser)))
//
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Saifur"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("saif55@gmail.com"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("Saifur123"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("1798277732"));
//
//
//    }
//    static String asJsonString(final Users putUser) {
//        try{
//            return new ObjectMapper().writeValueAsString(putUser);
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }

}
