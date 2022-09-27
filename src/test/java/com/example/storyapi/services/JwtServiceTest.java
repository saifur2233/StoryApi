package com.example.storyapi.services;

import com.example.storyapi.security.JWTUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtServiceTest {

    @MockBean
    private JWTUtility jwtUtility;
    @Autowired
    private UserDetailsServiceInfo userDetailsServiceInfo;

    @Autowired
    private JwtService jwtService;

    @Test
    @DisplayName("Test token Generation By email - Success")
    void testAuthenticate(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlmQGdtYWlsLmNvbSIsImV4cCI6MTY2NDE3NzM4NSwiaWF0IjoxNjY0MTU5Mzg1fQ.qO1Qw1CxoUU2dByxq8lgmhPfa-y6IEFi76ASCz3LWksJNaWFUxZk3-1hQE_R6o2n1K7EPNsLQYFqM3XGOt7eDw";
        final UserDetails userDetails = userDetailsServiceInfo.loadUserByUsername("saif@gmail.com");
        doReturn(token).when(jwtUtility).generateToken(userDetails);

        final String actualToken = jwtService.authenticate("saif@gmail.com");
        Assertions.assertSame(token, actualToken);
    }
}
