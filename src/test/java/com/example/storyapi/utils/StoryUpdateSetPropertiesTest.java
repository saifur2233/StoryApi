package com.example.storyapi.utils;

import com.example.storyapi.models.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoryUpdateSetPropertiesTest {
    @Autowired
    private StoryUpdateSetProperties storyUpdateSetProperties;
    @Test
    void testStorySetProperties(){
        Story mockstory = new Story("updated title test  fdfd  sdsdsd erwerer 02", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");
        Story story = new Story(1,"updated title test  fdfd  sdsdsd erwerer 01", "updated jhausjjhjka dfdffg dfdfdfdf cffgf fddfdfgdf dfdfdfdf rtrrttr");

        storyUpdateSetProperties.setUserProperties(story, mockstory);
    }
}
