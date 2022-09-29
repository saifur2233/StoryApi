package com.example.storyapi.security;

import com.example.storyapi.services.UserDetailsServiceInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JWTUtilityTest {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsServiceInfo userDetailsServiceInfo;

    private String email;

    private String token;

    @BeforeEach
    void setUp() {
        email = "saif@gmail.com";
        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlmQGdtYWlsLmNvbSIsImV4cCI6MTY2NDQyODA1MSwiaWF0IjoxNjY0NDEwMDUxfQ.Wonikn60OdbFmDIU-x9aCVvZmhH4RZE-VzKr74ohZlO91rcmw-Qc6J5fIO6cmuEV91EWcfD3Z-h002L79X-gkw";
    }

    @Test
    void testGetEmailFromToken(){

        String returnEmail = jwtUtility.getEmailFromToken(token);
        Assertions.assertEquals(email,returnEmail);
    }

    @Test
    void testGetExpirationDateFromToken(){
        Date date = jwtUtility.getExpirationDateFromToken(token);
        Assertions.assertNotNull(date);
    }

    @Test
    void testGenerateToken(){
        final UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername(email);
        String GetToken = jwtUtility.generateToken(userDetails);
        Assertions.assertNotNull(GetToken);
    }

    @Test
    void testValidateToken(){
        final UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername(email);
        Boolean value = jwtUtility.validateToken(token, userDetails);
        Assertions.assertTrue(value);
    }

}
