package com.example.demo.controller;

import com.example.demo.person.*;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return personService.findAll();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonDTO> findByEmail(@PathVariable("email") String email) {
        return personService.findByEmail(email);
    }

    @GetMapping("/broadsearch/{param}")
    public ResponseEntity<List<PersonDTO>> findCompound(@PathVariable("param") String param) {
        return personService.findCompound(param);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") String id) {
        return personService.findById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PersonDTO> findByName(@PathVariable("name") String name) {
        return personService.findByName(name);
    }

    @GetMapping("/relation/{relation}")
    public ResponseEntity<List<PersonDTO>> findByBusinessRelation(@PathVariable("relation") String relation) {
        return personService.findByBusinessRelation(relation);
    }

    @GetMapping("/company/{company}")
    public ResponseEntity<List<PersonDTO>> findByCompany(@PathVariable("company") String company) {
        return personService.findByCompany(company);
    }

    @GetMapping("/team/{internalTeam}")
    public ResponseEntity<List<PersonDTO>> findByTeam(@PathVariable("company") InternalTeam internalTeam) {
        return personService.findByTeam(internalTeam);
    }


    //        pattern to use:
//        |businessRelation:externalprivate,name: EP1,phoneNumber:EPphoneN1,email:EPemail1
//        |businessRelation:externalbusiness,name:EB1,phoneNumber:EBphoneN1,email:EBemail1,company:company1
//        |businessRelation:internal,name:I1,phoneNumber:Iphone1,email:Iemail1,internalTeam:HR,isManager:true
    @PostMapping(value = "/bulkImport",
            consumes = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<HttpStatus> bulkImport(@RequestBody String data) {
        return personService.bulkImport(data);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPerson(@RequestBody PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    @PutMapping("/email/{id}/{email}")
    public ResponseEntity<HttpStatus> updateEmail(@PathVariable("id") String id,
                                                  @PathVariable("email") String email) {
        return personService.updateEmail(id, email);
    }

    @PutMapping("/name/{id}/{name}")
    public ResponseEntity<HttpStatus> updateName(@PathVariable("id") String id,
                                                  @PathVariable("name") String name) {
        return personService.updateName(id, name);
    }

    @PutMapping("/phonenumber/{id}/{phonenumber}")
    public ResponseEntity<HttpStatus> updatePhoneNumber(@PathVariable("id") String id,
                                                  @PathVariable("phonenumber") String phonenumber) {
        return personService.updatePhoneNumber(id, phonenumber);
    }

    @PutMapping("/company/{id}/{company}")
    public ResponseEntity<HttpStatus> updateCompany(@PathVariable("id") String id,
                                                  @PathVariable("company") String company) {
        return personService.updateCompany(id, company);
    }

    @PutMapping("/team/{id}/{team}")
    public ResponseEntity<HttpStatus> updateTeam(@PathVariable("id") String id,
                                                  @PathVariable("team") String team) {
        return personService.updateTeam(id, team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") String id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String email,
                                                   @RequestParam(required = false) String company,
                                                   @RequestParam(required = false) String phoneNumber,
                                                   @RequestParam(required = false) InternalTeam internalTeam
    ) {
        return personService.updatePerson(id, name, email, company, phoneNumber, internalTeam);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") String id) {
        return personService.deleteById(id);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        return personService.deleteAll();
    }
}
