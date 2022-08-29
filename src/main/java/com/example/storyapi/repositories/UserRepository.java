package com.example.storyapi.repositories;

import com.example.storyapi.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Integer> {
    public boolean existsByEmail(String email);
    public Users findByEmail(String email);


}
