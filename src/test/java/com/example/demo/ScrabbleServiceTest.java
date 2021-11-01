package com.example.demo;

import com.example.demo.service.scrabble.ScrabbleService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrabbleServiceTest {

    @Autowired
    ScrabbleService scrabbleService;

    @Test
    public void testGetWordsReturnsAListOfWords() {
        List<String> example1 = scrabbleService.getWords("hat");
        Assert.assertNotNull(example1);
        Assert.assertEquals(6, example1.size());

        List<String> example2 = scrabbleService.getWords("aht");
        Assert.assertNotNull(example2);
        Assert.assertEquals(6, example2.size());
    }
}
