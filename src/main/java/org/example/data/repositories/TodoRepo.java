package org.example.data.repositories;

import org.example.data.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


public interface TodoRepo extends JpaRepository<Todo, Long> {
     Todo findByTaskName(String taskName);
     List<Todo> findByUserId(Long userId);
     Optional<Todo> findByIdAndUserId(Long todoId, Long userId);
}
