package com.example.demo.person;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
public class InternalPerson extends Person {
//    private final String businessRelation ="Internal";
    private InternalTeam internalTeam;
    private boolean isManager;

    public InternalPerson() {
    }

    public InternalPerson(String name, String phoneNumber, String email, InternalTeam internalTeam, boolean isManager) {
        super(BusinessRelation.INTERNAL,name, phoneNumber, email);
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

    @Override
    public String toString() {

        return "InternalEntity{" +
                "id='" + this.getId() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", internalTeam=" + internalTeam +
                ", isManager=" + isManager +
                '}';
    }
}
