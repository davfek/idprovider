package com.example.demo.config;

import com.example.demo.person.Person;
import com.example.demo.repository.PersonRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://mongodb:27017");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "mongodb");
    }

    //base data first init
    @Bean
    CommandLineRunner runner(PersonRepository personRepository) {
        return args -> {
            if (personRepository.findAll().isEmpty()) {
                List<Person> personList = DataImport.readDataFromFile();
                personRepository.insert(personList);
            }
        };
    }
}
