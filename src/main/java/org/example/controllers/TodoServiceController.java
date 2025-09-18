package org.example.controllers;



import jakarta.servlet.http.HttpSession;
import org.example.data.models.Todo;
import org.example.data.repositories.TodoRepo;
import org.example.dto.requests.todoRequest.TodoRequest;
import org.example.dto.responses.todoResponse.TodoResponse;
import org.example.service.TodoService;
import org.example.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/todo")
public class TodoServiceController {

    @Autowired
    private final TodoService todoService;
    private final TodoRepo todoRepo;

    public TodoServiceController(TodoService todoService, TodoRepo todoRepo) {
        this.todoService = todoService;
        this.todoRepo = todoRepo;
    }


    @PostMapping(path="/todo")
    public ResponseEntity<TodoResponse> addTodo(@RequestBody TodoRequest request, HttpSession session){
        Long userId = (Long) session.getAttribute("USER_ID");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        TodoResponse savedTodo = todoService.addTodo(request, userId);
        return ResponseEntity.ok(savedTodo);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponse>> getUserTodos(HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(todoService.getTodosForUser(userId));

    }
    @GetMapping("/todo/taskCompleted")
    public Optional<TodoResponse> getTodoByName(@RequestParam String taskName, HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");
        TodoResponse todoResponse = todoService.getTodoByName(taskName);
        if(todoResponse.getTaskName().equalsIgnoreCase(taskName)){
            return Optional.of(todoResponse);
        }
        return  Optional.empty();
    }

    @PutMapping("/todo/{todoId}/complete")
    public ResponseEntity<TodoResponse> markAsComplete(@PathVariable("todoId") Long todoId, HttpSession session){
            Long userId = (Long) session.getAttribute("USER_ID");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            TodoResponse updatedTodo = todoService.markAsComplete(userId, todoId);
            return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/todo/{userId}")
    public String deleteTodo(@RequestBody TodoRequest todoRequest, @PathVariable Long userId){
        Todo todo = Mapper.convertTodoRequestToTodo(todoRequest, userId);
        if(todo.getUserId().equals(userId)){
            todoService.deleteTask(userId);
        }
        return "Task has been deleted successfully";

    }


}
