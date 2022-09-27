package com.example.storyapi.Repository;

import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser(){
        Users user = new Users( "Saifur","saif88@gmail.com", "Saifur123", "1798277732");
        userRepository.save(user);
        Assertions.assertTrue(user.getId() > 0);
    }

}
