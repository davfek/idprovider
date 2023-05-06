package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entity")
public class ExternalPrivateEntity extends Entity {
    public ExternalPrivateEntity() {
    }

    public ExternalPrivateEntity(String name, String phoneNumber, String email) {
        super(name, phoneNumber, email, LocalDateTime.now());
    }
    @Override
    public String toString() {

        return "ExternalPrivateEntity{" +
                "id='" + this.getId() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", created=" + this.getCreated() +
                '}';
    }
}
