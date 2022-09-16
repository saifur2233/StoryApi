package com.example.storyapi.services;

import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.UserRouteProtection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserRouteProtection userRouteProtection;


    @Test
    @DisplayName("Test User findById Success")
    void testUserFindByIdSuccess() {
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123","1798277732");
        doReturn(Optional.of(mockUser)).when(userRepository).findById(1);

        Users returnedUser = userService.getUser(1);
        Assertions.assertSame(returnedUser, mockUser,"User findById");
    }

    @Test
    @DisplayName("Test User findAll")
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

    @Test
    @DisplayName("Test User Update")
    void testUserUpdate() {
        Users mockUser = new Users(1, "Saifur","saif55@gmail.com", "Saifur123","1798277732");
        doReturn(Optional.of(mockUser)).when(userRepository).save(mockUser);

        userRouteProtection.checkUserValidation(mockUser.getId());
        Optional<Users> userObj =  userRepository.findById(mockUser.getId());
        //Optional<Users> existEmail = userRepository.findByEmail(mockUser.getEmail());
        userService.setUserProperties(userObj.get(),mockUser);
        userRepository.save(userObj.get());
        Assertions.assertEquals(userObj.get(), mockUser, "User Update succesful");
    }
}
