package com.example.demo.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public List<Person> fetchAllPersons(){
        return personService.getAllPersons();
    }
}
