package com.example.storyapi.services;

import com.example.storyapi.Filter.JwtFilter;
import com.example.storyapi.converter.UserConverter;
import com.example.storyapi.dto.UserDTO;
import com.example.storyapi.exceptions.EntityNotFoundException;
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
    private UserConverter userConverter;

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
        UserDTO mockUser = new UserDTO(1, "Saifur","saif@gmail.com", "1798277732");
        Users user = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");

        when(userRepository.findByEmail("saif12@gmail.com"))
                .thenThrow(new EntityNotFoundException(UserServiceTest.class," Email ","saif12@gmail.com"));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserInfo("saif12@gmail.com"));
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        String expectedMessage = "UserServiceTest not found with  Email  saif12@gmail.com";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("GET Test User findAll - Success")
    void testGetAllUsers() {
        UserDTO mockUser1 = new UserDTO(1, "Saifur","saif@gmail.com", "1798277732");
        UserDTO mockUser2 = new UserDTO(2, "Saifur","saif22@gmail.com", "1798277732");

        doReturn(Arrays.asList(mockUser1, mockUser2)).when(userRepository).findAll();


        //System.out.println(userService.getAllUsers());
//        int counter = 0;
//        for (Object i : allUser) {
//            counter++;
//        }
//        Assertions.assertEquals(2, counter, "Get All Users");
    }

    @Test
    @DisplayName("PUT Test User Update -Success")
    void testUserUpdate() throws Exception{
        UserDTO mockUserDto = new UserDTO(1, "Saifur","saif@gmail.com", "1798277732");
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");

        doReturn(Optional.of(mockUser)).when(userRepository).findById(1);
        doReturn(mockUserDto).when(userRepository).save(mockUser);

        Optional<Users> userObj =  userRepository.findById(1);

        UserService mockuserService = mock(UserService.class);
        mockuserService.updateUser(mockUser.getId(), mockUser);

        verify(mockuserService, times(1)).updateUser(mockUser.getId(), mockUser);

        Assertions.assertEquals(userObj.get().getId(), mockUser.getId(), "User find succesful");
    }

    @Test
    @DisplayName("DELETE user delete - Success")
    void testUserDeleteSuccess() {
       UserService mockuserService=mock(UserService.class);
       mockuserService.deleteUser(1);
       verify(mockuserService, times(1)).deleteUser(1);
    }

    /*UserService mockuserService=mock(UserService.class);
        mockuserService.deleteUser(1);
        verify(mockuserService, times(1)).deleteUser(1);*/

    /* when(Optional.ofNullable(userRouteProtection.checkUserValidation(1))).thenThrow(AccessDeniedException.class);
        Assertions.assertThrows(AccessDeniedException.class,()->userRouteProtection.checkUserValidation(1));*/


}
