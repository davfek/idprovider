package com.example.demo.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EntityRepository extends MongoRepository<Entity, String> {
    @Query("{email:?0}")
    Optional<Entity> findEntityByEmail(String email);

    @Query("{name:?0}")
    List<Entity> findEntityByName(String name);

    @Query("{_class:?0}")
    List<Entity> findEntityByClass(String c);

    @Query("{company:?0}")
    List<Entity> findEntityByCompany(String company);

    @Query("{internalTeam:?0}")
    List<Entity> findEntityByTeam(InternalTeam team);

//    ResponseEntity<HttpStatus> importEntities();
//    //TODO add basic CRUD operations
}
