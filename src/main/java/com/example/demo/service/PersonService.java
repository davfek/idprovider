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

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    public ResponseEntity<List<Person>> findAll() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Person> findByEmail(String email) {
        Optional<Person> entityData = personRepository.findByEmail(email);
        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Person> findById(String id) {
        Optional<Person> entityData = personRepository.findById(id);
        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Person> findByName(String name) {
        Optional<Person> entityData = personRepository.findByName(name);
        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<List<Person>> findByClass(String _class) {
        if (_class.equalsIgnoreCase("internalentity")) {
            _class = "com.example.demo.entity.InternalEntity";
        }
        if (_class.equalsIgnoreCase("externalbusinessentity")) {
            _class = "com.example.demo.entity.ExternalBusinessEntity";
        }
        if (_class.equalsIgnoreCase("externalprivateentity")) {
            _class = "com.example.demo.entity.ExternalPrivateEntity";
        }
        List<Person> personData = personRepository.findByClass(_class);
        if (!personData.isEmpty()) {
            return new ResponseEntity<>(personData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Person>> findByCompany(String company) {
        List<Person> personData = personRepository.findByCompany(company);

        if (!personData.isEmpty()) {
            return new ResponseEntity<>(personData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Person>> findByTeam(InternalTeam internalTeam) {
        List<Person> personData = personRepository.findByTeam(internalTeam);

        if (!personData.isEmpty()) {
            return new ResponseEntity<>(personData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Person> createExternalBusinessPerson(ExternalBusinessPerson externalBusinessPerson){
        try {
            ExternalBusinessPerson entity1 = personRepository.save(externalBusinessPerson);
            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Person> createExternalPrivatePerson(ExternalPrivatePerson externalPrivatePerson){
        try {
            ExternalPrivatePerson entity1 = personRepository.save(externalPrivatePerson);
            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Person> createInternalPerson(InternalPerson internalPerson){
        try {
            InternalPerson entity1 = personRepository.save(internalPerson);
            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> updatePerson(String id, String name, String email, String company,String phoneNumber, InternalTeam internalTeam){
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
