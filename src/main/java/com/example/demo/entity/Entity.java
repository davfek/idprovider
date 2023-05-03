package com.example.demo.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "entity")
public abstract class Entity {
    @Id
    private String id;

    private String name;
    private String phoneNumber;
    @Indexed(unique = true)
    private String email;
    private LocalDateTime created;


    public Entity() {
    }



    public Entity(String name, String phoneNumber, String email, LocalDateTime created) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.created = created;
    }

}
