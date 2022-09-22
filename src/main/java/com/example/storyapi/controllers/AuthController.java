package com.example.storyapi.controllers;

import com.example.storyapi.models.Users;
import com.example.storyapi.security.JWTUtility;
import com.example.storyapi.services.AuthService;
import com.example.storyapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "${apiPrefix}")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private JwtService jwtService;
    private static final String COOKIE_NAME = "lexus";
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@RequestBody Users users, HttpServletResponse response){
        Users signupUsers = authService.signUp(users);
        String token = jwtService.authenticate(signupUsers.getEmail());
        Cookie myCookie = CookieSetToken(token);
        response.addCookie(myCookie);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupUsers.getEmail());
    }

    @GetMapping(value = "/verifyuser")
    public String verifyUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String email = null;
        if( cookies == null || cookies.length < 1 ) {
            return email;
        }else{
            for( Cookie cookie : cookies ) {
                if( (COOKIE_NAME).equals( cookie.getName() ) ) {
                    String cookieValue = cookie.getValue();
                    email = jwtUtility.getEmailFromToken(cookieValue);
                }
            }
        }
        return email;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signIn(@RequestBody Users users, HttpServletResponse response){
        Optional<Users> loggedUser = authService.signIn(users);
        if (loggedUser.isEmpty()) {
            return new ResponseEntity<>("Password didn't not match", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.authenticate(loggedUser.get().getEmail());
        Cookie myCookie = CookieSetToken(token);
        response.addCookie(myCookie);
        return ResponseEntity.status(HttpStatus.OK).body(loggedUser.get().getEmail());
    }

    protected Cookie CookieSetToken(String token){
        Cookie myCookie = new Cookie( "lexus", token );
        myCookie.setMaxAge(30000);
        myCookie.setHttpOnly(true);
        return myCookie;
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<Void> signout(HttpServletRequest request){
        SecurityContextHolder.clearContext();
        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(
                new Cookie[0])).filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        //authCookie.isPresent(cookie -> cookie.setMaxAge(0));

        return ResponseEntity.noContent().build();
    }

}
