package com.example.demo.person;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PersonDTOMapper implements Function<Person,PersonDTO> {
    @Override
    public PersonDTO apply(Person person){
        BusinessRelation businessRelation=person.getBusinessRelation();
        return switch (businessRelation) {
            case INTERNAL ->
                    new PersonDTO(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail(), person.getCreated(), ((InternalPerson) person).getInternalTeam(), ((InternalPerson) person).isManager());
            case EXTERNAL_BUSINESS ->
                    new PersonDTO(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail(), person.getCreated(), ((ExternalBusinessPerson) person).getCompany());
            case EXTERNAL_PRIVATE ->
                    new PersonDTO(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail(), person.getCreated());
        };
    }
}
