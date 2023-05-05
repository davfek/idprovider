package com.example.demo;

import com.example.demo.entity.Entity;
import com.example.demo.entity.EntityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootApplication
public class IdProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdProviderApplication.class, args);


    }

//    @Bean
//    CommandLineRunner runner(EntityRepository entityRepository, MongoTemplate mongoTemplate) {
//        entityRepository.deleteAll();
//        return args -> {
//            String email = "df@df.com";
//            ExternalPrivateEntity entity = new ExternalPrivateEntity(
//                    "Chris",
//                    "123456",
//                    email,
//                    LocalDateTime.now()
//            );
//            InternalEntity internalEntity=new InternalEntity(
//                    "David",
//                    "1234",
//                    "df@fd.com",
//                    LocalDateTime.now(),
//                    InternalTeam.IT_SUPPORT,
//                    true
//            );
//            ExternalBusinessEntity e = new ExternalBusinessEntity(
//                    "Chris",
//                    "123456",
//                    "asd@asd.com",
//                    LocalDateTime.now(),
//                    "Google"
//            );
// //           usingMongoTemplateAndQuery(personRepository, mongoTemplate, email, person);
////            entityRepository.findEntityByEmail(email).ifPresentOrElse(s->{
////                System.out.println(entity + " already exists");
////            },()->{   System.out.println("Inserting person " + entity);
////                entityRepository.insert(entity);});
////            entityRepository.insert(internalEntity);
////            entityRepository.insert(e);
//
//            entityRepository.insert(List.of(entity,internalEntity,e));
//        };
//    }

    private void usingMongoTemplateAndQuery(EntityRepository entityRepository, MongoTemplate mongoTemplate, String email, Entity entity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Entity> entityList = mongoTemplate.find(query, Entity.class);
        if (entityList.size() > 1) {
            throw new IllegalStateException("found to many person with email " + email);
        }
        if (entityList.isEmpty()) {
            System.out.println("Inserting person " + entity);
            entityRepository.insert(entity);
        }else {
            System.out.println(entity + " already exists");
        }
    }

}
