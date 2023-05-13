package com.example.demo.person;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PersonDTO{
    String id;
    String name;
    String phoneNumber;
    String email;
    LocalDateTime created;
    InternalTeam internalTeam=InternalTeam.NON_APPLICABLE;

    boolean isManager=false;
    String company="n/a";

    public PersonDTO() {
    }

    public PersonDTO(String id, String name, String phoneNumber, String email, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.created = created;
    }

    public PersonDTO(String id, String name, String phoneNumber, String email, LocalDateTime created, InternalTeam internalTeam, boolean isManager) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.created = created;
        this.internalTeam = internalTeam;
        this.isManager = isManager;
    }

    public PersonDTO(String id, String name, String phoneNumber, String email, LocalDateTime created, String company) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.created = created;
        this.company = company;
    }
}
