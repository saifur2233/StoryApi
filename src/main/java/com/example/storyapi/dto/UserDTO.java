package com.example.storyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime created_At = LocalDateTime.now();

    public UserDTO(int id,String name,String email, String phoneNumber){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
