package com.example.storyapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 500)
    @NotNull
    @NotBlank
    @NotEmpty(message = "Blog title can't empty")
    private String title;

    @Size(max = 4000)
    @NotNull
    @NotBlank
    @NotEmpty(message = "Blog description can't empty")
    private String description;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users author;

    private LocalDateTime created_At = LocalDateTime.now();

    public Story(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public Story(int id, String title, String description, Users author){
        this.id = id;
        this.title = title;
        this.description = description;
        this.author =author;
    }
    public Story(String title, String description){
        this.title = title;
        this.description = description;
    }
    @Override
    public int hashCode() {return id+author.hashCode();}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Story other = (Story) obj;
        return Objects.equals(getId(), other.id);
    }
}
