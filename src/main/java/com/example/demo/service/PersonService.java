package com.example.demo.service;

import com.example.demo.dto.PersonDTO;
import com.example.demo.dto.PersonDTOMapper;
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

    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream().map(personDTOMapper::mapToDTO).collect(Collectors.toList());

//        return new ResponseEntity<>(personRepository.findAll().
//                stream().
//                map(personDTOMapper::mapToDTO)
//                .collect(Collectors.toList())
//                , HttpStatus.OK);
    }

    public List<PersonDTO> findCompound(String param) {
        List<Person> personList = new ArrayList<>();
        if (personRepository.findByName(param).isPresent()) {
            personList.add(personRepository.findByName(param).get());
        }
        if (personRepository.findByPhoneNumber(param).isPresent()) {
            personList.add(personRepository.findByPhoneNumber(param).get());
        }
        if (personRepository.findByEmail(param).isPresent()) {
            personList.add(personRepository.findByEmail(param).get());
        }
        if (personList.size() < 1) {
            personList.addAll(personRepository.findByCompany(param));
        }
        if (personList.size() < 1) {
            personList.addAll(personRepository.findByTeam(InternalTeam.valueOf(param)));
        }
        List<PersonDTO> personDTOList = personList.stream().map(personDTOMapper::mapToDTO).toList();

        return personDTOList;
//        if (personList.size() > 0) {
//            return new ResponseEntity<>(personDTOList, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

    }

    public Optional<PersonDTO> findByEmail(String email) {
       return personRepository.findByEmail(email).map(personDTOMapper::mapToDTO);
//        if (entityData.isPresent()) {
//            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    public Optional<PersonDTO> findById(String id) {
        return personRepository.findById(id).map(personDTOMapper::mapToDTO);
//        if (entityData.isPresent()) {
//            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    public Optional<PersonDTO> findByName(String name) {
        return personRepository.findByName(name).map(personDTOMapper::mapToDTO);
//        if (entityData.isPresent()) {
//            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }
    //TODO
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

    public List<PersonDTO> findByCompany(String company) {
        return personRepository.findByCompany(company).stream().map(personDTOMapper::mapToDTO).toList();

//        if (!personData.isEmpty()) {
//            return new ResponseEntity<>(personData, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    public List<PersonDTO> findByTeam(InternalTeam internalTeam) {
        return personRepository.findByTeam(internalTeam).stream().map(personDTOMapper::mapToDTO).toList();

//        if (!personData.isEmpty()) {
//            return new ResponseEntity<>(personData, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    public Person createPerson(PersonDTO personDTO) {
            return personRepository.save(personDTOMapper.mapToPerson(personDTO));


    }

    public Person updateEmail(String id, String email) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setEmail(email);
            return personRepository.save(person.get());
        } else {
            return null;
        }
    }

    public Person updateName(String id, String name) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setName(name);
            return personRepository.save(person.get());
        } else {
            return null;
        }
    }

    public Person updatePhoneNumber(String id, String phonenumber) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setPhoneNumber(phonenumber);
            return personRepository.save(person.get());
        } else {
            return null;
        }
    }

    public Person updateCompany(String id, String company) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent() && person.get().getBusinessRelation().equals(BusinessRelation.EXTERNAL_BUSINESS)) {
            ((ExternalBusinessPerson)person.get()).setCompany(company);
            return personRepository.save(person.get());
        } else {
            return null;
        }
    }

    public Person updateTeam(String id, String team) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent() && person.get().getBusinessRelation().equals(BusinessRelation.INTERNAL)) {
            ((InternalPerson)person.get()).setInternalTeam(InternalTeam.valueOf(team));
            return personRepository.save(person.get());
        } else {
            return null;
        }
    }

    public Person updatePerson(String id, String name, String email, String company, String phoneNumber, InternalTeam internalTeam) {
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
            return personRepository.save(person1);
        } else return null;
    }

    public List<Person> bulkImport(String data) {

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

            return personRepository.findAll();
    }

    public boolean deleteById(String id) {
            personRepository.deleteById(id);
            Optional<Person> person=personRepository.findById(id);
            if (person.isPresent()){
                return false;
            }else return true;

    }

    public boolean deleteAll() {
        personRepository.deleteAll();
        if (personRepository.findAll().isEmpty()){
            return true;
        }else return false;


//        try {
//            personRepository.deleteAll();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }
}
