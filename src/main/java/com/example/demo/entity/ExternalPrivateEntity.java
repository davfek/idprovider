package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entity")
public class ExternalPrivateEntity extends Entity {
    public ExternalPrivateEntity(String name, String phoneNumber, String email, LocalDateTime created) {
        super(name, phoneNumber, email, created);
    }
}
