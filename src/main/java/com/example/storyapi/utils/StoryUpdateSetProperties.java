package com.example.storyapi.utils;

import com.example.storyapi.models.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryUpdateSetProperties {
    public void setUserProperties(Story currentStory, Story story) {
        currentStory.setTitle(story.getTitle());
        currentStory.setDescription(story.getDescription());
    }
}
