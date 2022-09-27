package com.example.storyapi.services;

import com.example.storyapi.converter.UserConverter;
import com.example.storyapi.dto.UserDTO;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.UserRouteProtection;
import com.example.storyapi.utils.UserUpdateSetProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserUpdateSetProperties userUpdateSetProperties;

    @Autowired
    private UserRouteProtection userRouteProtection;

    @Autowired
    private UserConverter userConverter;



    @Test
    @DisplayName("GET user find by email - Success")
    void testUserFindByEmailSuccess() {
        UserDTO mockUser = new UserDTO(1, "Saifur","saif@gmail.com", "1798277732");
        Users user = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        doReturn(Optional.of(user)).when(userRepository).findByEmail("saif@gmail.com");

        UserDTO returnedUser = userService.getUserInfo("saif@gmail.com");

        UserDTO expectedUser = userConverter.entityToDto(user);
        Assertions.assertEquals(mockUser, expectedUser, "User findByEmail");
    }

    @Test
    @DisplayName("GET user find by email - Not Found")
    void testUserFindByEmailNotFound() {
        when(userRepository.findByEmail("saif121212129999@gmail.com"))
                .thenThrow(new EntityNotFoundException(UserServiceTest.class," Email ","saif121212129999@gmail.com"));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserInfo("saif121212129999@gmail.com"));
        String actualMessage = exception.getMessage();
        //System.out.println(actualMessage);
        String expectedMessage = "UserServiceTest not found with  Email  saif121212129999@gmail.com";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("GET Test User findAll - Success")
    void testGetAllUsers() {
        UserDTO mockUserDto1 = new UserDTO(1, "Saifur","saif@gmail.com", "1798277732");
        UserDTO mockUserDto2 = new UserDTO(2, "Saifur","saif22@gmail.com", "1798277732");
        Users mockUser1 = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        Users mockUser2 = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");

        doReturn(Arrays.asList(mockUser1, mockUser2)).when(userRepository).findAll();

        List<UserDTO> users = userService.getAllUsers();
        Assertions.assertEquals(2, users.size(), "Get All Users");
    }

    @Test
    @DisplayName("PUT Test User Update -Success")
    void testUserUpdate() throws Exception{
        UserDTO mockUserDto = new UserDTO(1, "Saifur","saif@gmail.com", "1798277732");
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        Users mockUser2 = new Users(2, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        doReturn(Optional.of(mockUser)).when(userRepository).findById(1);
        doReturn(mockUserDto).when(userRepository).save(mockUser);
        doReturn(Optional.of(mockUser)).when(userRepository).findByEmail("saif@gmail.com");
        doNothing().when(userUpdateSetProperties).setUserProperties(mockUser,mockUser2);

        UserRouteProtection userRouteProtection1 = mock(UserRouteProtection.class);
        userRouteProtection1.checkUserValidation(1);
        verify(userRouteProtection1, times(1)).checkUserValidation(1);

        Optional<Users> userObj =  userRepository.findById(1);
        Assertions.assertEquals(mockUser.getId(),userObj.get().getId());

        userUpdateSetProperties.setUserProperties(mockUser, mockUser2);
        userRepository.save(mockUser2);
        UserDTO returnedDto = userConverter.entityToDto(mockUser);

    }


    @Test
    @DisplayName("DELETE user delete - Success")
    void testUserDeleteSuccess() {
       UserService mockuserService=mock(UserService.class);
       mockuserService.deleteUser(1);
       verify(mockuserService, times(1)).deleteUser(1);
    }

}
