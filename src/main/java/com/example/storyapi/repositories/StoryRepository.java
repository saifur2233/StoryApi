package com.example.storyapi.repositories;

import com.example.storyapi.models.Story;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story, Integer> {
}
