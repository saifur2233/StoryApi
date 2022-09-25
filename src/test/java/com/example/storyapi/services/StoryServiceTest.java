package com.example.storyapi.services;

import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Story;
import com.example.storyapi.repositories.StoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoryServiceTest {

    @Autowired
    private StoryService storyService;
    @Mock
    private StoryRepository storyRepository;

    @Test
    @DisplayName("GET Story find by id - Success")
    void testGetStoryByIdSuccess() {
        StoryDTO mockstoryDto = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        Story mockstory = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");
        doReturn(Optional.of(mockstory)).when(storyRepository).findById(1);

        StoryDTO returnedStory = storyService.getStory(1);
        Assertions.assertNotNull(returnedStory);
    }

    @Test
    @DisplayName("GET Story find by id - Not Found")
    void testStoryFindByIdNotFound() {
        StoryDTO mockstoryDto = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        Story mockstory = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        when(storyRepository.findById(100))
                .thenThrow(new EntityNotFoundException(StoryServiceTest.class," Id ", String.valueOf(100)));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> storyService.getStory(100));
        String actualMessage = exception.getMessage();
        //System.out.println("Message: "+actualMessage);
        String expectedMessage = "Story not found with id 100";
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("GET user email get All Story - Success")
    void testGetAllStoryByEmailSuccess() {
        StoryDTO mockstoryDto1 = new StoryDTO(1,"hello 01", "hjashalallkdaldlnadd", "saif@gmail.com", LocalDateTime.now());
        StoryDTO mockstoryDto2 = new StoryDTO(2,"hello 01", "hjashalallkdaldlnadd", "saif@gmail.com", LocalDateTime.now());
        doReturn(Arrays.asList(mockstoryDto1, mockstoryDto2)).when(storyRepository).findAllByauthorEmail("saif@gmail.com");

        List<StoryDTO> allStory = storyService.getUserEmailAllStory("saif@gmail.com");

        Assertions.assertNotNull(allStory);
    }

    @Test
    @DisplayName("GET user email get All Story - Not Found")
    void testGetAllStoryByEmailNotFound (){
        when(storyRepository.findAllByauthorEmail("saif121212@gmail.com"))
                .thenThrow(new EntityNotFoundException(StoryServiceTest.class," Email ","saif121212@gmail.com"));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> storyService.getUserEmailAllStory("saif121212@gmail.com"));
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        String expectedMessage = "Story not found with Email saif121212@gmail.com";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("POST Create Story - Success")
    void testCreateStory(){
        StoryDTO mockstoryDto = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        Story mockstory = new Story("updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        doReturn(mockstoryDto).when(storyRepository).save(mockstory);

        StoryService mockStoryService = mock(StoryService.class);
        mockStoryService.createStory(mockstory);

        verify(mockStoryService, times(1)).createStory(mockstory);
    }



    @Test
    @DisplayName("PUT Story update - Not Found")
    void testStoryUpdateNotFound(){
        Story mockstory = new Story("updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        when(storyRepository.findById(200))
                .thenThrow(new EntityNotFoundException(StoryServiceTest.class," Id ","200"));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> storyService.updateStory(200, mockstory));
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        String expectedMessage = "Story not found with id 200";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("DELETE Story delete - Success")
    void testStoryDeleteSuccess() {
        StoryService mockStoryService=mock(StoryService.class);
        mockStoryService.deleteStory(1);
        verify(mockStoryService, times(1)).deleteStory(1);
    }


    @Test
    @DisplayName("DELETE Story delete - Not Found")
    void testStoryDeleteNotFound(){
        when(storyRepository.findById(100))
                .thenThrow(new EntityNotFoundException(StoryServiceTest.class," Id ","100"));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> storyService.deleteStory(100));
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        String expectedMessage = "Story not found with id 100";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
