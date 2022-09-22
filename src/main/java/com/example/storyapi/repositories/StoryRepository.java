package com.example.storyapi.repositories;

import com.example.storyapi.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findAllByauthorEmail(String authorEmail);
}
