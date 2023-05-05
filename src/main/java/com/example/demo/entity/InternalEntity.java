package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entity")
public class InternalEntity extends Entity {
    private InternalTeam internalTeam;
    private boolean isManager;

    public InternalEntity(String name, String phoneNumber, String email, InternalTeam internalTeam, boolean isManager) {
        super(name, phoneNumber, email, LocalDateTime.now());
        this.internalTeam = internalTeam;
        this.isManager = isManager;
    }

    public InternalTeam getInternalTeam() {
        return internalTeam;
    }

    public void setInternalTeam(InternalTeam internalTeam) {
        this.internalTeam = internalTeam;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
