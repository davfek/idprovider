package com.example.demo.person;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "person")
public class ExternalBusinessPerson extends Person {
//    private final String businessRelation ="ExternalBusiness";
    private String company;

    public ExternalBusinessPerson() {
    }

    public ExternalBusinessPerson(String name, String phoneNumber, String email, String company) {
        super(name, phoneNumber, email, LocalDateTime.now());
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    @Override
    public String toString() {

        return "ExternalBusinessEntity{" +
                "id='" + this.getId() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", created=" + this.getCreated() +
                ", company=" + company +
                '}';
    }
}
