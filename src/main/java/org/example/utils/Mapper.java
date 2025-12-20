package org.example.utils;

import org.example.data.models.Todo;
import org.example.data.models.Users;
import org.example.dto.requests.userRequest.LoginRequest;
import org.example.dto.requests.userRequest.RegisterRequest;
import org.example.dto.requests.todoRequest.TodoRequest;
import org.example.dto.responses.userResponse.LoginResponse;
import org.example.dto.responses.userResponse.RegisterResponse;
import org.example.dto.responses.todoResponse.TodoResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class Mapper {

    private  final PasswordEncoder passwordEncoder;

    public Mapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public static Users convertUserRequestToUser(RegisterRequest registerRequest) {
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        return user;
    }

    public static RegisterResponse convertUserToResponse(Users savedUser) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedUser.getUserId());
        registerResponse.setUsername(savedUser.getUsername());
        registerResponse.setEmail(savedUser.getEmail());
        registerResponse.setPassword(savedUser.getPassword());
        registerResponse.setMessage("Successfully registered");
        return registerResponse;
    }
    public static LoginResponse convertUserToLoginResponse(Users user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getUserId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setMessage("Successfully logged in");
        return loginResponse;
    }
    public static Users convertLoginRequestToUser(LoginRequest loginRequest) {
        Users user = new Users();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        return user;
    }

    public static Todo convertTodoRequestToTodo(TodoRequest request, Long userId) {
        Todo todo = new Todo();
        todo.setTaskName(request.getTaskName());
        todo.setCategory(request.getCategory());
        todo.setDueDate(request.getDueDate());
        todo.setCreatedDate(LocalDateTime.now());
        todo.setUserId(userId);
        todo.setCompleted(false);

        return todo;
    }

    public static TodoResponse convertTodoToTodoResponse(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setId(todo.getId());
        todoResponse.setTaskName(todo.getTaskName());
        todoResponse.setCategory(String.valueOf(todo.getCategory()));
        todoResponse.setCreatedDate(LocalDateTime.now());
        todoResponse.setDueDate(LocalDateTime.now());
        todoResponse.setCompleted(true);
        todoResponse.setCompletedDate(LocalDateTime.now());
        todoResponse.setUserId(todo.getUserId());
        return todoResponse;
    }
}
