package com.example.demo.config;

import com.example.demo.person.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataImport {
    public DataImport() {
    }

    public static List<Person> readDataFromFile() throws IOException {
        Path path = Path.of("/app/import.txt");
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        String line;
        List<Person> personList = new ArrayList<Person>();
        while ((line = reader.readLine()) != null) {
            Person person;
            String[] values = line.trim().split("[,:]");
            String businessRelation = values[1];
            switch (businessRelation.toLowerCase()) {
                case "externalprivate":
                    person = new ExternalPrivatePerson(values[3], values[5], values[7]);
                    personList.add(person);
                    break;
                case "externalbusiness":
                    person = new ExternalBusinessPerson(values[3], values[5], values[7], values[9]);
                    personList.add(person);
                    break;
                case "internal":
                    person = new InternalPerson(values[3], values[5], values[7], InternalTeam.valueOf(values[9]), Boolean.parseBoolean(values[11]));
                    personList.add(person);
                    break;
            }
        }
        return personList;
    }
}