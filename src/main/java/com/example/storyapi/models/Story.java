package com.example.storyapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    @NotEmpty(message = "Blog title can't empty")
    private String title;

    @NotNull
    @NotBlank
    @NotEmpty(message = "Blog description can't empty")
    private String description;


    @ManyToOne
    private Users author;

    private LocalDateTime created_At = LocalDateTime.now();
}