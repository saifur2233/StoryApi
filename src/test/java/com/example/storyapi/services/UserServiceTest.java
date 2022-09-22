package com.example.storyapi.services;

import com.example.storyapi.Filter.JwtFilter;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.UserRouteProtection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserRouteProtection userRouteProtection;

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
    @DisplayName("GET user find by email - Success")
    void testUserFindByEmailSuccess() {
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123","1798277732");
        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail("saif@gmail.com");

        Users returnedUser = userService.getUserInfo("saif@gmail.com");
        Assertions.assertSame(returnedUser, mockUser,"User findByEmail");
    }

    @Test
    @DisplayName("GET user find by email - Not Found")
    void testUserFindByEmailNotFound() {
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123","1798277732");
        when(userRepository.findByEmail("saif22@gmail.com")).thenReturn(Optional.of(mockUser)).thenThrow(new EntityNotFoundException("Entity Not Found"));

        Users returnedUser = userService.getUserInfo("saif22@gmail.com");
        Assertions.assertSame(returnedUser, mockUser,"User findByEmail");
    }

    @Test
    @DisplayName("GET Test User findAll - Success")
    void testGetAllUsers() {
        Users mockUser1 = new Users(1, "Saifur","saif@gmail.com", "Saifur123","1798277732");
        Users mockUser2 = new Users(2, "Saif","saif22@gmail.com", "Saifur123","1798277732");
        doReturn(Arrays.asList(mockUser1, mockUser2)).when(userRepository).findAll();

        Iterable<Users> users = userService.getAllUsers();
        int counter = 0;
        for (Object i : users) {
            counter++;
        }
        Assertions.assertEquals(2, counter, "User findAll should return 2 users");
    }

//    @Test
//    @DisplayName("PUT Test User Update -Success")
//    void testUserUpdate() throws Exception{
//        Users mockUser = new Users(1, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
//        doReturn(mockUser).when(userRepository).save(mockUser);
//
//        Optional<Users> userObj =  userRepository.findById(mockUser.getId());
//        Users updatedUser = userService.updateUser(mockUser.getId(), mockUser);
//        Assertions.assertEquals(userObj.get(), updatedUser, "User Update succesful");
//    }

    @Test
    @DisplayName("DELETE user delete - Success")
    void testUserDeleteSuccess() {
        doNothing().when(userRepository).deleteById(4);
        userRouteProtection.checkUserValidation(4);
        userService.deleteUser(4);
        Assertions.assertNull(userRepository.findById(4));
    }


}
