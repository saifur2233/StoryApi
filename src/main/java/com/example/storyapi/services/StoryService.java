package com.example.storyapi.services;

import com.example.storyapi.converter.StoryConverter;
import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Story;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.StoryRepository;
import com.example.storyapi.utils.CreateStoryRouteProtection;
import com.example.storyapi.utils.StoryRouteProtection;
import com.example.storyapi.utils.StoryUpdateSetProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private StoryUpdateSetProperties storyUpdateSetProperties;

    public List<StoryDTO> getAllStories(Integer pageNumber, Integer pageSize){
        Pageable pageableObj = PageRequest.of(pageNumber, pageSize);
        Page<Story> storyPage = storyRepository.findAll(pageableObj);
        List<Story> allStory = storyPage.getContent();
        return storyConverter.listStoryDto(allStory);
    }

    public StoryDTO getStory(Integer id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        return storyConverter.entityToDto(story.get());
    }

    public List<StoryDTO> getUserEmailAllStory(String email){
        List<Story> stories = storyRepository.findAllByauthorEmail(email);
        if (stories.size() == 0) throw new EntityNotFoundException(Story.class, "Email", String.valueOf(email));
        return storyConverter.listStoryDto(stories);
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
        storyUpdateSetProperties.setUserProperties(storyObj.get(), story);
        return storyConverter.entityToDto(storyRepository.save(storyObj.get()));
    }

    public void deleteStory(Integer id){
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
        routeProtection.checkUserValidation(story.get().getAuthor().getId());
        storyRepository.deleteById(id);
    }


}
