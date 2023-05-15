package com.example.demo.service;

import com.example.demo.person.*;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonDTOMapper personDTOMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonDTOMapper personDTOMapper) {
        this.personRepository = personRepository;
        this.personDTOMapper = personDTOMapper;
    }

    public ResponseEntity<List<PersonDTO>> findAll() {
        return new ResponseEntity<>(personRepository.findAll().
                stream().
                map(personDTOMapper::mapToDTO)
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    public ResponseEntity<PersonDTO> findByEmail(String email) {
        Optional<PersonDTO> entityData = personRepository.findByEmail(email).map(personDTOMapper::mapToDTO);
        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PersonDTO> findById(String id) {
        Optional<PersonDTO> entityData = personRepository.findById(id).map(personDTOMapper::mapToDTO);
        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PersonDTO> findByName(String name) {
        Optional<PersonDTO> entityData = personRepository.findByName(name).map(personDTOMapper::mapToDTO);
        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PersonDTO>> findByBusinessRelation(String relation) {
        BusinessRelation businessRelation = null;
        switch (relation.toLowerCase()) {
            case "internal":
                businessRelation = BusinessRelation.INTERNAL;
                break;
            case "externalbusiness":
                businessRelation = BusinessRelation.EXTERNAL_BUSINESS;
                break;
            case "externalprivate":
                businessRelation = BusinessRelation.EXTERNAL_PRIVATE;
                break;
            default:
                break;
        }
        List<PersonDTO> personDTOList = personRepository.findByBusinessRelation(businessRelation).stream().map(personDTOMapper::mapToDTO).toList();
        if (!personDTOList.isEmpty()) {
            return new ResponseEntity<>(personDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PersonDTO>> findByCompany(String company) {
        List<PersonDTO> personData = personRepository.findByCompany(company).stream().map(personDTOMapper::mapToDTO).toList();

        if (!personData.isEmpty()) {
            return new ResponseEntity<>(personData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PersonDTO>> findByTeam(InternalTeam internalTeam) {
        List<PersonDTO> personData = personRepository.findByTeam(internalTeam).stream().map(personDTOMapper::mapToDTO).toList();

        if (!personData.isEmpty()) {
            return new ResponseEntity<>(personData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> createPerson(PersonDTO personDTO){
        try {
            personRepository.save(personDTOMapper.mapToPerson(personDTO));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<Person> createExternalBusinessPerson(ExternalBusinessPerson externalBusinessPerson) {
//        try {
//            ExternalBusinessPerson entity1 = personRepository.save(externalBusinessPerson);
//            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public ResponseEntity<Person> createExternalPrivatePerson(ExternalPrivatePerson externalPrivatePerson) {
//        try {
//            ExternalPrivatePerson entity1 = personRepository.save(externalPrivatePerson);
//            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public ResponseEntity<Person> createInternalPerson(InternalPerson internalPerson) {
//        try {
//            InternalPerson entity1 = personRepository.save(internalPerson);
//            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public ResponseEntity<HttpStatus> updatePerson(String id, String name, String email, String company, String phoneNumber, InternalTeam internalTeam) {
        Optional<Person> entity = personRepository.findById(id);
        if (entity.isPresent()) {
            Person person1 = entity.get();
            if (name != null && name.length() > 0) {
                person1.setName(name);
            }
            if (email != null && email.length() > 0) {
                person1.setEmail(email);
            }
            if (company != null && company.length() > 0) {
                ((ExternalBusinessPerson) person1).setCompany(company);
            }
            if (phoneNumber != null && phoneNumber.length() > 0) {
                person1.setName(phoneNumber);
            }
            if (internalTeam != null) {
                ((InternalPerson) person1).setInternalTeam(internalTeam);
            }
            personRepository.save(person1);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> bulkImport(String data) {
        try {
            String[] sArray = data.split("\\|");
            List<Person> personArrayList = new ArrayList<>();
            for (String s : sArray) {
                Person person1;
                String[] sArrayArray = s.split("[,:]");
                String businessRelation = sArrayArray[1];
                switch (businessRelation.toLowerCase()) {
                    case "externalprivate":
                        person1 = new ExternalPrivatePerson(sArrayArray[3], sArrayArray[5], sArrayArray[7]);
                        personArrayList.add(person1);
                        break;
                    case "externalbusiness":
                        person1 = new ExternalBusinessPerson(sArrayArray[3], sArrayArray[5], sArrayArray[7], sArrayArray[9]);
                        personArrayList.add(person1);
                        break;
                    case "internal":
                        person1 = new InternalPerson(sArrayArray[3], sArrayArray[5], sArrayArray[7], InternalTeam.valueOf(sArrayArray[9]), Boolean.parseBoolean(sArrayArray[11]));
                        personArrayList.add(person1);
                        break;
                    default:
                        break;
                }
            }
            personRepository.insert(personArrayList);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteById(String id) {
        try {
            personRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            personRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
