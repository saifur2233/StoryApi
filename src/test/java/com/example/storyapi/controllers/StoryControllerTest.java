package com.example.storyapi.controllers;

import com.example.storyapi.Filter.JwtFilter;
import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.services.StoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoryControllerTest {
    @MockBean
    private StoryService storyService;

    private MockMvc mockMvc;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setup(){
        mockMvc= MockMvcBuilders
                .webAppContextSetup(webApplicationContext).addFilter(jwtFilter, "/*").build();
    }

    @Test
    @DisplayName("GET Find one story - Found")
    void testGetUserSuccess() throws Exception{
        StoryDTO mockstoryDto = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");
        when(storyService.getStory(mockstoryDto.getId())).thenReturn(mockstoryDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories/search/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("hello 01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("hjashalallkdaldlnadd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorEmail").value("saifur@gmail.com"));
    }

    @Test
    @DisplayName("GET Find one story - Not Found")
    void testGetUserNotFound() throws Exception{
        StoryDTO mockstoryDto = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");
        when(storyService.getStory(3)).thenReturn(mockstoryDto).thenThrow(new EntityNotFoundException("Story Not Found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories/search/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET AllStory By User Email - Success")
    void testGetUserEmailAllStory() throws Exception{
        StoryDTO mockstoryDto1 = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");
        StoryDTO mockstoryDto2 = new StoryDTO(2,"hello 02", "hjashalallkdaldlnadd", "saifur@gmail.com");
        when(storyService.getUserEmailAllStory(mockstoryDto1.getAuthorEmail())).thenReturn(Arrays.asList(mockstoryDto1, mockstoryDto2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories/{email}","saifur@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("GET AllStory By User Email - Not Found")
    void testGetUserEmailAllStoryNotFound() throws Exception{
        StoryDTO mockstoryDto1 = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");
        StoryDTO mockstoryDto2 = new StoryDTO(2,"hello 02", "hjashalallkdaldlnadd", "saifur@gmail.com");
        when(storyService.getUserEmailAllStory("saifur22@gmail.com")).thenReturn(Arrays.asList(mockstoryDto1, mockstoryDto2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories/{email}","saifur22@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("GET All Blog - Found")
    void testFindAllUser() throws Exception{
        StoryDTO mockstoryDto1 = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");
        StoryDTO mockstoryDto2 = new StoryDTO(2,"hello 02", "hjashalallkdaldlnadd", "saifur@gmail.com");
        when(storyService.getAllStories(0,6))
                .thenReturn(Arrays.asList(mockstoryDto1, mockstoryDto2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @DisplayName("POST Create story - Success")
    void testCreateStory()throws Exception{
        StoryDTO postStoryDto = new StoryDTO("hello 01", "hjashalallkdaldlnadd");
        StoryDTO mockstoryDto = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");
        doReturn(mockstoryDto).when(storyService).createStory(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postStoryDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("hello 01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("hjashalallkdaldlnadd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorEmail").value("saifur@gmail.com"));
    }

    @Test
    @DisplayName("PUT update story - Success")
    void testUpdateStory()throws Exception{
        StoryDTO putStoryDto = new StoryDTO("hello 01", "hjashalallkdaldlnadd");
        StoryDTO mockstoryDto = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saifur@gmail.com");

        doReturn(mockstoryDto).when(storyService).updateStory(eq(mockstoryDto.getId()),any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(putStoryDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("hello 01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("hjashalallkdaldlnadd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorEmail").value("saifur@gmail.com"));
    }

    @Test
    @DisplayName("DELETE Story delete - Success")
    void testStoryDelete() throws Exception{
        doNothing().when(storyService).deleteStory(3);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stories/{id}", 3))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE story delete - Not Found")
    void testStoryDeleteNotFound() throws Exception{
        doNothing().when(storyService).deleteStory(3);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stories/{id}", 3))
                .andExpect(status().isNoContent());
    }

    static String asJsonString(final StoryDTO story) {
        try{
            return new ObjectMapper().writeValueAsString(story);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
