package com.example.demo.config;

import com.example.demo.entity.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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

    @Bean
    CommandLineRunner runner(EntityRepository entityRepository) {
        return args -> {
            List<Entity> entityList = readDataFromFile();
            entityRepository.insert(entityList);
        };
    }

    public List<Entity> readDataFromFile() throws IOException {
        Path path = Path.of("/app/import.txt");
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        String line;
        List<Entity> entityList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            Entity entity;
            String[] values = line.trim().split("[,:]");
            String businessRelation = values[1];
            switch (businessRelation.toLowerCase()) {
                case "externalprivate":
                    entity = new ExternalPrivateEntity(values[3], values[5], values[7]);
                    entityList.add(entity);
                    break;
                case "externalbusiness":
                    entity = new ExternalBusinessEntity(values[3], values[5], values[7], values[9]);
                    entityList.add(entity);
                    break;
                case "internal":
                    entity = new InternalEntity(values[3], values[5], values[7], InternalTeam.valueOf(values[9]), Boolean.parseBoolean(values[11]));
                    entityList.add(entity);
                    break;
            }
        }
        return entityList;
    }
}
