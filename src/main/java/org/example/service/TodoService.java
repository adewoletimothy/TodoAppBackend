package org.example.service;


import org.example.data.models.Todo;
import org.example.data.models.User;
import org.example.data.repositories.TodoRepo;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.TodoRequest;
import org.example.dto.responses.TodoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.utils.Mapper;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(TodoRepo todoRepo, UserRepository userRepository) {
        this.todoRepo = todoRepo;
        this.userRepository = userRepository;
    }

    public TodoResponse addTodo(TodoRequest request, String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Todo todo = Mapper.convertTodoRequestToTodo(request);
            Todo savedTodo = todoRepo.save(todo);
            return Mapper.convertTodoToTodoResponse(savedTodo);
        }
        throw new IllegalArgumentException("User not found");
    }

    public List<Todo> getAllTodo(){
        return todoRepo.findAll();
    }

    public Todo getTodoById(TodoResponse todoResponse, String userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {

            todoResponse.setId() = todoRepo.findById(user.get().getId()).orElse(null);
        }
        return todoRepo.findById(id);
    }
    public Optional<Todo> getTodoByName(String name){
        if(todoRepo.findByTaskName(name).isPresent()){
            return todoRepo.findByTaskName(name);
        }
        return  Optional.empty();
    }
    public void markAsComplete(String taskName){
        Optional<Todo> todo = todoRepo.findByTaskName(taskName);
        if(todo.isPresent()){
            Todo myTodo = todo.get();
            myTodo.setCompleted(true);
            todoRepo.save(myTodo);
        }
    }

    public void deleteTask(String id) {
         todoRepo.deleteById(id);
    }
}
