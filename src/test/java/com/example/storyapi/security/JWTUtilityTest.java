package com.example.storyapi.security;

import com.example.storyapi.services.UserDetailsServiceInfo;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void testGetEmailFromToken(){
        String email = "saif@gmail.com";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlmQGdtYWlsLmNvbSIsImV4cCI6MTY2NDM0OTcyMywiaWF0IjoxNjY0MzMxNzIzfQ.cDc6M4M3-U7MJuPfQmhezGFNBHGRLG1HkfnJuxEdWUbZYNp_jtQS0esbZkyE5LPWwxkYG_LppGcoQWouicOZOw";

        String returnEmail = jwtUtility.getEmailFromToken(token);
        Assertions.assertEquals(email,returnEmail);
    }

    @Test
    void testGetExpirationDateFromToken(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlmQGdtYWlsLmNvbSIsImV4cCI6MTY2NDM0OTcyMywiaWF0IjoxNjY0MzMxNzIzfQ.cDc6M4M3-U7MJuPfQmhezGFNBHGRLG1HkfnJuxEdWUbZYNp_jtQS0esbZkyE5LPWwxkYG_LppGcoQWouicOZOw";

        Date date = jwtUtility.getExpirationDateFromToken(token);
        Assertions.assertNotNull(date);
    }

    @Test
    void testGenerateToken(){
        String email = "saif@gmail.com";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlmQGdtYWlsLmNvbSIsImV4cCI6MTY2NDM0OTcyMywiaWF0IjoxNjY0MzMxNzIzfQ.cDc6M4M3-U7MJuPfQmhezGFNBHGRLG1HkfnJuxEdWUbZYNp_jtQS0esbZkyE5LPWwxkYG_LppGcoQWouicOZOw";

        final UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername(email);

        String GetToken = jwtUtility.generateToken(userDetails);

        Assertions.assertNotNull(GetToken);
    }

    @Test
    void testValidateToken(){
        String email = "saif@gmail.com";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlmQGdtYWlsLmNvbSIsImV4cCI6MTY2NDM0OTcyMywiaWF0IjoxNjY0MzMxNzIzfQ.cDc6M4M3-U7MJuPfQmhezGFNBHGRLG1HkfnJuxEdWUbZYNp_jtQS0esbZkyE5LPWwxkYG_LppGcoQWouicOZOw";
        final UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername(email);

        Boolean value = jwtUtility.validateToken(token, userDetails);
        Assertions.assertTrue(value);
    }

}
