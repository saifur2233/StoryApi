package com.example.storyapi.converter;

import com.example.storyapi.dto.UserDTO;
import com.example.storyapi.models.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UserConverter {

    public UserDTO entityToDto(Users user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCreated_At(user.getCreated_At());
        return dto;
    }
    public List<UserDTO> listUserDto(List<Users> users){
        return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
    }
    public Iterable<UserDTO> iterableStoryDto(Iterable<Users> users){
        return StreamSupport.stream(users.spliterator(), false).toList().stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

}
