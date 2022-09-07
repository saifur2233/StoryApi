package com.example.storyapi.services;

import com.example.storyapi.models.Story;
import com.example.storyapi.models.User;
import com.example.storyapi.repositories.StoryRepository;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public Iterable<Story> getAllStories(){
        return storyRepository.findAll();
    }

    public Story getStory(int id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        return story.get();
    }

    public Story createStory(Story story){
        return storyRepository.save(story);
    }

    public Story updateStory(int id, Story story){
        Optional<Story> storyObj =  storyRepository.findById(id);
        if (storyObj.isEmpty()){
            throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        }
        storyObj.get().setTitle(story.getTitle());
        storyObj.get().setDescription(story.getDescription());
        storyObj.get().setAuthorEmail(story.getAuthorEmail());
        storyRepository.save(storyObj.get());
        return storyObj.get();
    }

    public Story deleteStory(int id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isPresent()){
            storyRepository.deleteById(id);
            return story.get();
        }
        throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
    }
}
