package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScrabbleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    /**
     * The tests of the given examples
     * @throws Exception
     */
    @Test
    @WithUserDetails("pedro")
    public void controllerTest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/scrabble/words/hat")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString =  result.getResponse().getContentAsString();
        List<?> resultAsList = mapper.readValue(resultAsString, List.class);
        Assertions.assertEquals(6, resultAsList.size());


        MvcResult result2 = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/scrabble/words/aht")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString2 =  result2.getResponse().getContentAsString();
        List<?> resultAsList2 = mapper.readValue(resultAsString2, List.class);
        Assertions.assertEquals(6, resultAsList2.size());


        MvcResult result3 = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/scrabble/words/HAT")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString3 =  result3.getResponse().getContentAsString();
        List<?> resultAsList3 = mapper.readValue(resultAsString3, List.class);
        Assertions.assertEquals(6, resultAsList3.size());


        MvcResult result4 = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/scrabble/words/zzz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString4 =  result4.getResponse().getContentAsString();
        List<?> resultAsList4 = mapper.readValue(resultAsString4, List.class);
        Assertions.assertEquals(0, resultAsList4.size());
    }
}
