package com.example.storyapi.utils;

import com.example.storyapi.models.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserUpdateSetPropertiesTest {

    @Autowired
    private UserUpdateSetProperties userUpdateSetProperties;

    @Test
    void testUserSetProperties(){
        Users mockUser1 = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        Users mockUser2 = new Users(1, "Saifur rahman","saif@gmail.com", "Saifur123", "1798277732");

        userUpdateSetProperties.setUserProperties(mockUser1,mockUser2);
    }

}
