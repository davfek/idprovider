package com.example.demo;

import com.example.demo.person.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootApplication
public class IdProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdProviderApplication.class, args);

    }

    private void usingMongoTemplateAndQuery(PersonRepository entityRepository, MongoTemplate mongoTemplate, String email, Person entity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Person> entityList = mongoTemplate.find(query, Person.class);
        if (entityList.size() > 1) {
            throw new IllegalStateException("found to many person with email " + email);
        }
        if (entityList.isEmpty()) {
            System.out.println("Inserting person " + entity);
            entityRepository.insert(entity);
        }else {
            System.out.println(entity + " already exists");
        }
    }

}
