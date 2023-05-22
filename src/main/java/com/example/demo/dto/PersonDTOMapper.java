package com.example.demo.dto;

import com.example.demo.person.*;
import org.springframework.stereotype.Service;

@Service
public class PersonDTOMapper  {

    public PersonDTO mapToDTO(Person person){
        BusinessRelation businessRelation=person.getBusinessRelation();
        return switch (businessRelation) {
            case INTERNAL ->
                    new PersonDTO(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail(), ((InternalPerson) person).getInternalTeam(), ((InternalPerson) person).isManager());
            case EXTERNAL_BUSINESS ->
                    new PersonDTO(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail(), ((ExternalBusinessPerson) person).getCompany());
            case EXTERNAL_PRIVATE ->
                    new PersonDTO(person.getId(), person.getName(), person.getPhoneNumber(), person.getEmail());
        };
    }

    public Person mapToPerson(PersonDTO personDTO){
        if (personDTO.getInternalTeam()== InternalTeam.NON_APPLICABLE && personDTO.getCompany().equals("n/a")){
            return new ExternalPrivatePerson(personDTO.getName(), personDTO.getPhoneNumber(), personDTO.getEmail());
        }else if (personDTO.getInternalTeam()==InternalTeam.NON_APPLICABLE && !personDTO.getCompany().equals("n/a")){
            return new ExternalBusinessPerson(personDTO.getName(), personDTO.getPhoneNumber(), personDTO.getEmail(), personDTO.getCompany());
        }else {
            return new InternalPerson(personDTO.getName(), personDTO.getPhoneNumber(), personDTO.getEmail(), personDTO.getInternalTeam(), personDTO.isManager());
        }
    }
}
