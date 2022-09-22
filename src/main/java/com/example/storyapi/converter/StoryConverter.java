package com.example.storyapi.converter;

import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.models.Story;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class StoryConverter {

    public StoryDTO entityToDto(Story story){
        StoryDTO dto = new StoryDTO();
        dto.setId(story.getId());
        dto.setTitle(story.getTitle());
        dto.setDescription(story.getDescription());
        dto.setAuthorEmail(story.getAuthor().getEmail());
        dto.setCreatedAt(story.getCreated_At());
        return dto;
    }

    public Iterable<StoryDTO> iterableStoryDto(Iterable<Story> stories){
        return StreamSupport.stream(stories.spliterator(), false).toList().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    public List<StoryDTO> listStoryDto(List<Story> stories){
        return stories.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
