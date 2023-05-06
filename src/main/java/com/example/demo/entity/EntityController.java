package com.example.demo.entity;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public ResponseEntity<List<Entity>> fetchAllEntities() {
        return new ResponseEntity<>(entityService.getAllEntities(), HttpStatus.OK);
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

    @GetMapping("/byName/{name}")
    public ResponseEntity<Entity> findEntityByName(@PathVariable("name") String name) {
        Optional<Entity> entityData = entityRepository.findEntityByName(name);

        if (entityData.isPresent()) {
            return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/byClass/{_class}")
    public ResponseEntity<List<Entity>> findEntityByClass(@PathVariable("_class") String _class) {
        if (_class.equalsIgnoreCase("internalentity")) {
            _class = "com.example.demo.entity.InternalEntity";
        }
        if (_class.equalsIgnoreCase("externalbusinessentity")) {
            _class = "com.example.demo.entity.ExternalBusinessEntity";
        }
        if (_class.equalsIgnoreCase("externalprivateentity")) {
            _class = "com.example.demo.entity.ExternalPrivateEntity";
        }

        List<Entity> entityData = entityRepository.findEntityByClass(_class);
        if (!entityData.isEmpty()) {
            return new ResponseEntity<>(entityData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/byCompany/{company}")
    public ResponseEntity<List<Entity>> findEntityByCompany(@PathVariable("company") String company) {
        List<Entity> entityData = entityRepository.findEntityByCompany(company);

        if (!entityData.isEmpty()) {
            return new ResponseEntity<>(entityData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/byTeam/{internalTeam}")
    public ResponseEntity<List<Entity>> findEntityByTeam(@PathVariable("company") InternalTeam internalTeam) {
        List<Entity> entityData = entityRepository.findEntityByTeam(internalTeam);

        if (!entityData.isEmpty()) {
            return new ResponseEntity<>(entityData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/BulkImport")
    public void bulkImport(String path) throws IOException {
//        Path path1=Path.of(path);
        BufferedReader reader=new BufferedReader(new FileReader(String.valueOf(path)));
        String line;
        List<Entity> entityList=new ArrayList<>();
        while ((line=reader.readLine())!=null){
            Entity entity;
            String[] values=line.trim().split("[,:]");
            String businessRelation=values[1];
            switch (businessRelation.toLowerCase()){
                case "externalprivate":
                    entity=new ExternalPrivateEntity(values[3],values[5],values[7]);
                    entityList.add(entity);
                    break;
                case "externalbusiness":
                    entity=new ExternalBusinessEntity(values[3],values[5],values[7],values[9]);
                    entityList.add(entity);
                    break;
                case "internal":
                    entity=new InternalEntity(values[3],values[5],values[7],InternalTeam.valueOf(values[9]),Boolean.parseBoolean(values[11]));
                    entityList.add(entity);
                    break;
            }
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

    //    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateEntity(@PathVariable("id") String id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String email,
                                                   @RequestParam(required = false) String company,
                                                   @RequestParam(required = false) String phoneNumber,
                                                   @RequestParam(required = false) InternalTeam internalTeam
    ) {
        Optional<Entity> entity = entityRepository.findById(id);
        if (entity.isPresent()) {
            Entity entity1 = entity.get();
            if (name != null && name.length() > 0) {
                entity1.setName(name);
            }
            if (email != null && email.length() > 0) {
                entity1.setEmail(email);
            }
            if (company != null && company.length() > 0) {
                ((ExternalBusinessEntity) entity1).setCompany(company);
            }
            if (phoneNumber != null && phoneNumber.length() > 0) {
                entity1.setName(phoneNumber);
            }
            if (internalTeam != null) {
                ((InternalEntity) entity1).setInternalTeam(internalTeam);
            }
            entityRepository.save(entity1);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
