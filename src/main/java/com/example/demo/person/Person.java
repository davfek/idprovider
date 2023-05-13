package com.example.demo.person;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "person")
public abstract class Person {
    @Id
    private String id;
    private BusinessRelation businessRelation;

    private String name;
    private String phoneNumber;
    @Indexed(unique = true)
    private String email;
    private LocalDateTime created;


    public Person() {
    }



    public Person(BusinessRelation businessRelation,String name, String phoneNumber, String email, LocalDateTime created) {
        this.businessRelation=businessRelation;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}