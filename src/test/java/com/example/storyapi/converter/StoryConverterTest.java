package com.example.storyapi.converter;

import com.example.storyapi.models.Story;
import com.example.storyapi.models.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoryConverterTest {
    @Autowired
    private StoryConverter storyConverter;

    @Test
    void testEntityToDto(){
       Story mockstory = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", new Users("saif@gmail.com","Saifur123"));
        storyConverter.entityToDto(mockstory);
    }

    @Test
    void testListStoryDto(){
        Story mockstory1 = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", new Users("saif@gmail.com","Saifur123"));
        Story mockstory2 = new Story(2,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr", new Users("saif@gmail.com","Saifur123"));
        storyConverter.listStoryDto(Arrays.asList(mockstory1,mockstory2));
    }
}
