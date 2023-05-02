package com.example.demo.person;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person,String> {
    Optional<Person> findPersonByEmail(String email);
    //TODO add basic CRUD operations
}
