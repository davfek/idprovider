package com.example.demo.repository;

import com.example.demo.person.BusinessRelation;
import com.example.demo.person.Person;
import com.example.demo.person.InternalTeam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    @Query("{email:?0}")
    Optional<Person> findByEmail(String email);

    @Query("{name:?0}")
    Optional<Person> findByName(String name);

    @Query("{phoneNumber:?0}")
    Optional<Person> findByPhoneNumber(String phoneNumber);

    @Query("{company:?0}")
    List<Person> findByCompany(String company);

    @Query("{internalTeam:?0}")
    List<Person> findByTeam(InternalTeam team);
    @Query("{businessRelation:?0}")
    List<Person> findByBusinessRelation(BusinessRelation relation);


}
