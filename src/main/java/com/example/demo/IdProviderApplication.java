package com.example.demo;

import com.example.demo.person.BusinessRelation;
import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class IdProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdProviderApplication.class, args);

    }

    @Bean
    CommandLineRunner runner(PersonRepository personRepository, MongoTemplate mongoTemplate) {
        return args -> {
            String email = "df@df.com";
            Person person = new Person(
                    BusinessRelation.INTERNAL_EMPLOYEE,
                    "Chris",
                    "123456",
                    email,
                    LocalDateTime.now()
            );
 //           usingMongoTemplateAndQuery(personRepository, mongoTemplate, email, person);
            personRepository.findPersonByEmail(email).ifPresentOrElse(s->{
                System.out.println(person + " already exists");
            },()->{   System.out.println("Inserting person " + person);
                personRepository.insert(person);});
        };
    }

    private void usingMongoTemplateAndQuery(PersonRepository personRepository, MongoTemplate mongoTemplate, String email, Person person) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Person> personList = mongoTemplate.find(query, Person.class);
        if (personList.size() > 1) {
            throw new IllegalStateException("found to many person with email " + email);
        }
        if (personList.isEmpty()) {
            System.out.println("Inserting person " + person);
            personRepository.insert(person);
        }else {
            System.out.println(person + " already exists");
        }
    }

}
