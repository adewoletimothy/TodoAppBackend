package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.data.models.Todo;
import org.example.data.models.Users;
import org.example.data.repositories.TodoRepo;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.todoRequest.TodoRequest;
import org.example.dto.responses.todoResponse.TodoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepository userRepository;


    @Autowired
    public TodoService(TodoRepo todoRepo, UserRepository userRepository) {
        this.todoRepo = todoRepo;
        this.userRepository = userRepository;
    }

    public TodoResponse addTodo(TodoRequest request, Long userId) {
//        log.info("Attempting adding todo: {}", request.getTaskName());
//        log.info("for user with userId: {}", userId);

        Optional<Users> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
//            log.info("user found: {}", user.get().getUsername());
            Todo todo = Mapper.convertTodoRequestToTodo(request, userId);
//            log.info("save todo: {}", todo);
            Todo savedTodo = todoRepo.save(todo);
            return Mapper.convertTodoToTodoResponse(savedTodo);
        }
//        log.warn("Addition failed: user {} not found", user.get().getUsername());
        throw new IllegalArgumentException("User not found");
    }

    public List<TodoResponse> getTodosForUser(Long userId) {
        List<Todo> todos = todoRepo.findByUserId(userId);
        List<TodoResponse> todoResponseList = new ArrayList<>();
        for (Todo todo : todos) {
            TodoResponse todoResponse = Mapper.convertTodoToTodoResponse(todo);
            todoResponseList.add(todoResponse);
        }
        return todoResponseList;
    }

    public TodoResponse getTodoById(Long todoId) {
        for (Todo todo : todoRepo.findAll()) {
            TodoResponse todoResponse = Mapper.convertTodoToTodoResponse(todo);
            if (todoResponse.getId().equals(todoId)) {
                return todoResponse;
            }
        }
        throw new RuntimeException("Todo with id " + todoId + " not found");
    }

    public TodoResponse getTodoByName(String taskName) {
        Todo todo = todoRepo.findByTaskName(taskName);
        TodoResponse todoResponse = Mapper.convertTodoToTodoResponse(todo);
        return todoResponse;
    }

    public TodoResponse markAsComplete(Long userId, Long todoId) {
        Todo todo = todoRepo.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        todo.setCompleted(true);
        Todo savedCompleteTodo = todoRepo.save(todo);
        return Mapper.convertTodoToTodoResponse(savedCompleteTodo);
    }

    public String deleteTask(Long id) {
        if (!todoRepo.existsById(id)) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }
        todoRepo.deleteById(id);
        return "Todo with id " + id + " deleted successfully";
    }

}
