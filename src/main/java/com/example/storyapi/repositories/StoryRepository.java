package com.example.storyapi.repositories;

import com.example.storyapi.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoryRepository extends JpaRepository<Story, Integer> {
}
