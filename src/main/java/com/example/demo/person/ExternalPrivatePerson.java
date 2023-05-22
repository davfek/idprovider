package com.example.demo.person;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
public class ExternalPrivatePerson extends Person {
//    private final String businessRelation ="ExternalPrivate";
    public ExternalPrivatePerson() {
    }

    public ExternalPrivatePerson(String name, String phoneNumber, String email) {
        super(BusinessRelation.EXTERNAL_PRIVATE,name, phoneNumber, email);
    }

    @Override
    public String toString() {

        return "ExternalPrivateEntity{" +
                "id='" + this.getId() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
