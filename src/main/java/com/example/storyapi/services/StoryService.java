package com.example.storyapi.services;

import com.example.storyapi.converter.StoryConverter;
import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Story;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.StoryRepository;
import com.example.storyapi.utils.CreateStoryRouteProtection;
import com.example.storyapi.utils.StoryRouteProtection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryRouteProtection routeProtection;

    @Autowired
    private CreateStoryRouteProtection createStoryRouteProtection;

    @Autowired
    private StoryConverter storyConverter;

    public Iterable<StoryDTO> getAllStories(){
        return storyConverter.iterableStoryDto(storyRepository.findAll());
    }

    public StoryDTO getStory(Integer id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        return storyConverter.entityToDto(story.get());
    }

    public StoryDTO createStory(Story story){
        Users storyauthor = createStoryRouteProtection.checkUserValidation();
        story.setAuthor(storyauthor);
        return storyConverter.entityToDto(storyRepository.save(story));
    }

    public StoryDTO updateStory(Integer id, Story story){
        Optional<Story> storyObj =  storyRepository.findById(id);
        if (storyObj.isEmpty()){
            throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        }
        routeProtection.checkUserValidation(storyObj.get().getAuthor().getId());
        setUserProperties(storyObj.get(), story);
        return storyConverter.entityToDto(storyRepository.save(storyObj.get()));
    }

    protected void setUserProperties(Story currentStory, Story story) {
        currentStory.setTitle(story.getTitle());
        currentStory.setDescription(story.getDescription());
    }
    public void deleteStory(Integer id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        routeProtection.checkUserValidation(story.get().getAuthor().getId());
        storyRepository.deleteById(id);
    }


}
