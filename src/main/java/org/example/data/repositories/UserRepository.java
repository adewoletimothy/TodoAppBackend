package org.example.data.repositories;


import org.example.data.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users,Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Users findByUsername(String username);
    Optional<Users> findByUserId(Long userId);
}
