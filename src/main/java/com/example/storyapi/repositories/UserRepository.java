package com.example.storyapi.repositories;

import com.example.storyapi.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByEmail(String email);

}
