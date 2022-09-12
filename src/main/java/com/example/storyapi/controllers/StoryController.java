package com.example.storyapi.controllers;

import com.example.storyapi.models.Story;
import com.example.storyapi.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping
    public ResponseEntity<Iterable<Story>> getAllStories(){
        Iterable<Story> stories = storyService.getAllStories();
        return ResponseEntity.status(HttpStatus.OK).body(stories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Story> getStory(@PathVariable Integer id) {
        Story story = storyService.getStory(id);
        return ResponseEntity.status(HttpStatus.OK).body(story);
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story){
        Story newStory = storyService.createStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStory);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Integer id, @RequestBody Story story){
        Story updated = storyService.updateStory(id, story);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Story> deleteStory(@PathVariable Integer id){
        storyService.deleteStory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
