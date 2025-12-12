package org.example.data.repositories;


import org.example.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
