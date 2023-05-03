package com.example.demo.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class EntityService {
    private final EntityRepository entityRepository;
    public List<Entity> getAllEntities(){
        return entityRepository.findAll();
    }

}
