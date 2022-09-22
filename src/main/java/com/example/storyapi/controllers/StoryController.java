package com.example.storyapi.controllers;

import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.models.Story;
import com.example.storyapi.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${apiPrefix}/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize
    ){
        List<StoryDTO> stories = storyService.getAllStories(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(stories);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<StoryDTO> getStory(@PathVariable Integer id) {
        StoryDTO story = storyService.getStory(id);
        return ResponseEntity.status(HttpStatus.OK).body(story);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<List<StoryDTO>> getUserEmailAllStory(@PathVariable String email) {
        List<StoryDTO> stories = storyService.getUserEmailAllStory(email);
        return ResponseEntity.status(HttpStatus.OK).body(stories);
    }

    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@RequestBody Story story){
        StoryDTO newStory = storyService.createStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStory);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Integer id, @RequestBody Story story){
        StoryDTO updated = storyService.updateStory(id, story);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Story> deleteStory(@PathVariable Integer id){
        storyService.deleteStory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
