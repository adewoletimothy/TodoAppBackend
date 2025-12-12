package org.example.utils;

import org.example.data.models.Todo;
import org.example.data.models.User;
import org.example.dto.requests.RegisterRequest;
import org.example.dto.requests.TodoRequest;
import org.example.dto.responses.RegisterResponse;
import org.example.dto.responses.TodoResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Mapper {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User convertUserRequestToUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }

    public static RegisterResponse convertUserToResponse(User savedUser) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedUser.getId());
        registerResponse.setUsername(savedUser.getUsername());
        registerResponse.setEmail(savedUser.getEmail());
        registerResponse.setPassword(savedUser.getPassword());
        registerResponse.setMessage("Successfully registered");
        return registerResponse;
    }

    public static Todo convertTodoRequestToTodo(TodoRequest request) {
        Todo todo = new Todo();
        todo.setTaskName(request.getTaskName());
        todo.setCategory(request.getCategory());
        todo.setCompleted(false);
        todo.setUserId(request.getUserId());
        return todo;
    }

    public static TodoResponse convertTodoToTodoResponse(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setId(todo.getId());
        todoResponse.setTaskName(todo.getTaskName());
        todoResponse.setCategory(todo.getCategory());
        todoResponse.setCompleted(todo.isCompleted());
        todoResponse.setUserId(todo.getUserId());
        return todoResponse;
    }
}
