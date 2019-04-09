package me.ssoon.demobootweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String hello(final @RequestParam("id") Person person) {
        return "hello " + person.getName();
    }

    @GetMapping("/message")
    public String message(final @RequestBody String body) {
        return body;
    }

    @GetMapping("/jsonMessage")
    public Person jsonMessage(final @RequestBody Person person) {
        return person;
    }
}
