package com.example.demo.person;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
}
