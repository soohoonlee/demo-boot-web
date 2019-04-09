package me.ssoon.demobootweb;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void hello() throws Exception {
        final Person person = new Person();
        person.setName("soohoon");
        final Person savedPerson = personRepository.save(person);

        this.mockMvc.perform(get("/hello")
            .param("id", savedPerson.getId().toString()))
            .andDo(print())
            .andExpect(content().string("hello soohoon"));
    }

    @Test
    public void helloStatic() throws Exception {
        this.mockMvc.perform(get("/index.html"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello Index")));
    }

    @Test
    public void mobileHelloStatic() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello Mobile")))
            .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }

    @Test
    public void stringMessage() throws Exception {
        this.mockMvc.perform(get("/message")
            .content("hello"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello"));
    }

    @Test
    public void jsonMessage() throws Exception {
        final Person person = new Person();
        person.setId(2019l);
        person.setName("soohoon");

        final String jsonString = objectMapper.writeValueAsString(person);

        this.mockMvc.perform(get("/jsonMessage")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(jsonString))
            .andDo(print())
            .andExpect(status().isOk());
    }
}