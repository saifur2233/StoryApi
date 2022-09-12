package com.example.storyapi.services;

import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Story;
import com.example.storyapi.repositories.StoryRepository;
import com.example.storyapi.utils.CreateStoryRouteProtection;
import com.example.storyapi.utils.StoryRouteProtection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryRouteProtection routeProtection;

    @Autowired
    private CreateStoryRouteProtection createStoryRouteProtection;

    public Iterable<Story> getAllStories(){
        return storyRepository.findAll();
    }

    public Story getStory(Integer id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        return story.get();
    }

    public Story createStory(Story story){
        int authorId = createStoryRouteProtection.checkUserValidation();
        story.setAuthor(authorId);
        return storyRepository.save(story);
    }

    public Story updateStory(Integer id, Story story){
        Optional<Story> storyObj =  storyRepository.findById(id);
        if (storyObj.isEmpty()){
            throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        }
        routeProtection.checkUserValidation(storyObj.get().getAuthor());
        setUserProperties(storyObj.get(), story);
        return storyRepository.save(storyObj.get());
    }

    protected void setUserProperties(Story currentStory, Story story) {
        currentStory.setTitle(story.getTitle());
        currentStory.setDescription(story.getDescription());
    }
    public void deleteStory(Integer id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        routeProtection.checkUserValidation(story.get().getAuthor());
        storyRepository.deleteById(id);
    }


}
