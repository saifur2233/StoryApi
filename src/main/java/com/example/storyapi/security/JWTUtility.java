package com.example.storyapi.security;

import com.example.storyapi.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {
    private static final long serialVersionUID = 234234523523L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    private String secretKey = "saifur";

    // retrive username from jwt token
    public String getEmailFromToken(String token){
        return getClaimFromToken(token, Claims:: getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims :: getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token from user
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToekn(claims, user.getEmail());
    }

    //while creating the token
    //1. Define claims of the token
    //2. sign the JWt using the HS512 algorithm and scret key.

    private String doGenerateToekn(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    //validate token
    public Boolean validateToken(String token, User user){
        final String email = getEmailFromToken(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token));
    }
}