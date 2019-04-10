package me.ssoon.demobootweb;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.oxm.Marshaller;
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

    @Autowired
    Marshaller marshaller;

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
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2019))
            .andExpect(jsonPath("$.name").value("soohoon"));
    }

    @Test
    public void xmlMessage() throws Exception {
        final Person person = new Person();
        person.setId(2019l);
        person.setName("soohoon");

        final StringWriter stringWriter = new StringWriter();
        final Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        final String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/jsonMessage")
            .contentType(APPLICATION_XML)
            .accept(APPLICATION_XML)
            .content(xmlString))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("person/id").string("2019"))
            .andExpect(xpath("person/name").string("soohoon"));
    }
}