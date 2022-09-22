package com.example.storyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {
    private int id;
    private String title;
    private String description;
    private String authorEmail;
    private LocalDateTime createdAt;

    public StoryDTO(String title, String description){
        this.title = title;
        this.description = description;
    }
    public StoryDTO(int id, String title, String description, String authorEmail){
        this.id =id;
        this.title = title;
        this.description = description;
        this.authorEmail = authorEmail;
    }
}
