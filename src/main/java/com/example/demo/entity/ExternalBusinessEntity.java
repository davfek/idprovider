package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entity")
public class ExternalBusinessEntity extends Entity{
    private String company;

    public ExternalBusinessEntity(String name, String phoneNumber, String email, LocalDateTime created, String company) {
        super(name, phoneNumber, email, created);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
