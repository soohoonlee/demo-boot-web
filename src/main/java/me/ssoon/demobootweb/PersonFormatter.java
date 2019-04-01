package me.ssoon.demobootweb;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class PersonFormatter implements Formatter<Person> {

    @Override
    public Person parse(final String name, final Locale locale) throws ParseException {
        final Person person = new Person();
        person.setName(name);
        return person;
    }

    @Override
    public String print(final Person person, final Locale locale) {
        return person.toString();
    }
}
