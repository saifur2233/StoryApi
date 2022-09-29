package com.example.storyapi.services;

import com.example.storyapi.converter.StoryConverter;
import com.example.storyapi.dto.StoryDTO;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Story;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.StoryRepository;
import com.example.storyapi.utils.CreateStoryRouteProtection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private StoryConverter storyConverter;

    @MockBean
    private CreateStoryRouteProtection createStoryRouteProtection;

    @Test
    @DisplayName("GET Test for story -Success")
    void testGetAllStory(){
        StoryDTO mockstoryDto1 = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        StoryDTO mockstoryDto2 = new StoryDTO(2,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        Story mockstory1 = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");
        Story mockstory2 = new Story(2,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        doReturn(Arrays.asList(mockstoryDto1,mockstoryDto2)).when(storyConverter).listStoryDto(Arrays.asList(mockstory1,mockstory2));

        storyService.getAllStories(0,6);
        List<Story> allStory = Arrays.asList(mockstory1,mockstory2);
        List<StoryDTO> stories = storyConverter.listStoryDto(allStory);
        Assertions.assertEquals(2, stories.size(), "Get All Story");


    }

    @Test
    @DisplayName("GET Story find by id - Success")
    void testGetStoryByIdSuccess() {
        StoryDTO mockstoryDto = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        Story mockstory = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");
        doReturn(Optional.of(mockstory)).when(storyRepository).findById(1);
        doReturn(mockstoryDto).when(storyConverter).entityToDto(mockstory);

        StoryDTO returnedStory = storyService.getStory(1);
        Optional<Story> findstory = storyRepository.findById(1);
        StoryDTO actualstory = storyConverter.entityToDto(findstory.get());
        Assertions.assertEquals(mockstoryDto, returnedStory);
        Assertions.assertSame(mockstoryDto,actualstory);
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
        Users mockUser = new Users(1, "Saifur","saif@gmail.com", "Saifur123", "1798277732");
        StoryDTO mockstoryDto = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");
        Story mockstory = new Story("updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");
        Story poststory = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        doReturn(poststory).when(storyRepository).save(mockstory);
        doReturn(mockUser).when(createStoryRouteProtection).checkUserValidation();
        doReturn(mockstoryDto).when(storyConverter).entityToDto(mockstory);

        CreateStoryRouteProtection createStoryRouteProtection = mock(CreateStoryRouteProtection.class);
        createStoryRouteProtection.checkUserValidation();
        verify(createStoryRouteProtection, times(1)).checkUserValidation();

        Story story = storyRepository.save(mockstory);
        StoryDTO storyDTO = storyConverter.entityToDto(story);
//        assertEquals(mockstoryDto, storyDTO, "Stories should be same");
    }

    @Test
    @DisplayName("PUT Story update - Success")
    void testStoryUpdate(){
        Story mockstory = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");
        StoryDTO mockstoryDto = new StoryDTO(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", "saif@gmail.com");

        doReturn(Optional.of(mockstory)).when(storyRepository).findById(1);
        doReturn(mockstoryDto).when(storyRepository).save(mockstory);

        Optional<Story> storyObj =  storyRepository.findById(1);
        Assertions.assertEquals(storyObj.get().getId(), mockstory.getId(), "Story find succesful");

        StoryService mockStoryService = mock(StoryService.class);
        mockStoryService.updateStory(1, mockstory);
        verify(mockStoryService, times(1)).updateStory(1, mockstory);
    }


    @Test
    @DisplayName("PUT Story update - Not Found")
    void testStoryUpdateNotFound(){
        Story mockstory = new Story("updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        when(storyRepository.findById(200))
                .thenThrow(new EntityNotFoundException(StoryServiceTest.class," Id ","200"));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> storyService.updateStory(200, mockstory));
        String actualMessage = exception.getMessage();

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
        String expectedMessage = "Story not found with id 100";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
