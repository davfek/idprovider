package com.example.demo.controller;

import com.example.demo.dto.PersonDTO;
import com.example.demo.person.*;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return new ResponseEntity<>(personService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonDTO> findByEmail(@PathVariable("email") String email) {
        Optional<PersonDTO> personDTO=personService.findByEmail(email);
        if (personDTO.isPresent()){
            return new ResponseEntity<>(personDTO.get(),HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/broadsearch/{param}")
    public ResponseEntity<List<PersonDTO>> findCompound(@PathVariable("param") String param) {
        List<PersonDTO> personList=personService.findCompound(param);
        if (personList.size()>0){
            return new ResponseEntity<>(personList,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") String id) {
        Optional<PersonDTO> personDTO=personService.findById(id);
        if (personDTO.isPresent()){
            return new ResponseEntity<>(personDTO.get(),HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PersonDTO> findByName(@PathVariable("name") String name) {
        Optional<PersonDTO> personDTO=personService.findByName(name);
        if (personDTO.isPresent()){
            return new ResponseEntity<>(personDTO.get(),HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/relation/{relation}")
    public ResponseEntity<List<PersonDTO>> findByBusinessRelation(@PathVariable("relation") String relation) {
        return personService.findByBusinessRelation(relation);
    }

    @GetMapping("/company/{company}")
    public ResponseEntity<List<PersonDTO>> findByCompany(@PathVariable("company") String company) {
        List<PersonDTO> personDTOList=personService.findByCompany(company);
        if (personDTOList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<>(personDTOList,HttpStatus.OK);
    }

    @GetMapping("/team/{internalTeam}")
    public ResponseEntity<List<PersonDTO>> findByTeam(@PathVariable("company") InternalTeam internalTeam) {
        List<PersonDTO> personDTOList=personService.findByTeam(internalTeam);
        if (personDTOList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<>(personDTOList,HttpStatus.OK);
    }


    //        pattern to use:
//        |businessRelation:externalprivate,name: EP1,phoneNumber:EPphoneN1,email:EPemail1
//        |businessRelation:externalbusiness,name:EB1,phoneNumber:EBphoneN1,email:EBemail1,company:company1
//        |businessRelation:internal,name:I1,phoneNumber:Iphone1,email:Iemail1,internalTeam:HR,isManager:true
    @PostMapping(value = "/bulkImport",
            consumes = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<List<Person>> bulkImport(@RequestBody String data) {
        List<Person> personList= personService.bulkImport(data);
        if (personList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }else return new ResponseEntity<>(personList,HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody PersonDTO personDTO) {
        Person person = personService.createPerson(personDTO);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }else return new ResponseEntity<>(person,HttpStatus.CREATED);
    }

    @PutMapping("/email/{id}/{email}")
    public ResponseEntity<Person> updateEmail(@PathVariable("id") String id,
                                                  @PathVariable("email") String email) {
        Person person= personService.updateEmail(id, email);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @PutMapping("/name/{id}/{name}")
    public ResponseEntity<Person> updateName(@PathVariable("id") String id,
                                                  @PathVariable("name") String name) {
        Person person= personService.updateName(id, name);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @PutMapping("/phonenumber/{id}/{phonenumber}")
    public ResponseEntity<Person> updatePhoneNumber(@PathVariable("id") String id,
                                                  @PathVariable("phonenumber") String phonenumber) {
        Person person= personService.updatePhoneNumber(id, phonenumber);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @PutMapping("/company/{id}/{company}")
    public ResponseEntity<Person> updateCompany(@PathVariable("id") String id,
                                                  @PathVariable("company") String company) {
        Person person= personService.updateCompany(id, company);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @PutMapping("/team/{id}/{team}")
    public ResponseEntity<Person> updateTeam(@PathVariable("id") String id,
                                                  @PathVariable("team") String team) {
        Person person= personService.updateTeam(id, team);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") String id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String email,
                                                   @RequestParam(required = false) String company,
                                                   @RequestParam(required = false) String phoneNumber,
                                                   @RequestParam(required = false) InternalTeam internalTeam
    ) {

        Person person = personService.updatePerson(id, name, email, company, phoneNumber, internalTeam);
        if (person==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") String id) {
        if (personService.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        if(personService.deleteAll()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
