package com.example.demo.entity;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/entities")
@AllArgsConstructor
public class EntityController {

    private final EntityService entityService;
    @Autowired
    private final EntityRepository entityRepository;

    @GetMapping
    public List<Entity> fetchAllEntities() {
        return entityService.getAllEntities();
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<Entity> findEntityByEmail(@PathVariable("email") String email) {
        Optional<Entity> entityData = entityRepository.findEntityByEmail(email);

        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/ExternalBusinessEntity")
    public ResponseEntity<Entity> createExternalBusinessEntity(@RequestBody ExternalBusinessEntity entity) {
        try {
            ExternalBusinessEntity entity1 = entityRepository.save(entity);
            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ExternalPrivateEntity")
    public ResponseEntity<Entity> createExternalPrivateEntity(@RequestBody ExternalPrivateEntity entity) {
        try {
            ExternalPrivateEntity entity1 = entityRepository.save(entity);
            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/InternalEntity")
    public ResponseEntity<Entity> createEntity(@RequestBody InternalEntity entity) {
        try {
            InternalEntity entity1 = entityRepository.save(entity);
            return new ResponseEntity<>(entity1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ExternalBusinessEntity/{id}")
    public ResponseEntity<Entity> updateExternalBusinessEntity(@PathVariable("id") String id, @RequestBody ExternalBusinessEntity entity) {
        Optional<Entity> entityData = entityRepository.findById(id);

        if (entityData.isPresent()) {
            ExternalBusinessEntity entity1=(ExternalBusinessEntity) entityData.get();
            entity1.setCompany(entity.getCompany());
            entity1.setName(entity.getName());
            entity1.setPhoneNumber(entity.getPhoneNumber());
            entity1.setEmail(entity.getEmail());
            return new ResponseEntity<>(entityRepository.save(entity1),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }   @PutMapping("/ExternalPrivateEntity/{id}")
    public ResponseEntity<Entity> updatePrivateEntity(@PathVariable("id") String id, @RequestBody ExternalPrivateEntity entity) {
        Optional<Entity> entityData = entityRepository.findById(id);

        if (entityData.isPresent()) {
            ExternalPrivateEntity entity1=(ExternalPrivateEntity) entityData.get();
            entity1.setName(entity.getName());
            entity1.setPhoneNumber(entity.getPhoneNumber());
            entity1.setEmail(entity.getEmail());
            return new ResponseEntity<>(entityRepository.save(entity1),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }   @PutMapping("/InternalEntity/{id}")
    public ResponseEntity<Entity> updateInternalEntity(@PathVariable("id") String id, @RequestBody InternalEntity entity) {
        Optional<Entity> entityData = entityRepository.findById(id);

        if (entityData.isPresent()) {
            InternalEntity entity1=(InternalEntity) entityData.get();
            entity1.setManager(entity.isManager());
            entity1.setInternalTeam(entity.getInternalTeam());
            entity1.setName(entity.getName());
            entity1.setPhoneNumber(entity.getPhoneNumber());
            entity1.setEmail(entity.getEmail());
            return new ResponseEntity<>(entityRepository.save(entity1),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEntity(@PathVariable("id") String id) {
        try {
            entityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllEntity() {
        try {
            entityRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
