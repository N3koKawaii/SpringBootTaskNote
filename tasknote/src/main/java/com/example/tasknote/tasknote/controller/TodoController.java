package com.example.tasknote.tasknote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tasknote.tasknote.exception.ResourceNotFoundException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.repository.UserRepository;
import com.example.tasknote.tasknote.requestDto.TodoRequest;
import com.example.tasknote.tasknote.responseDto.TodoResponseDTO;
import com.example.tasknote.tasknote.service.TodoService;
import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private final UserRepository userRepository;

    public TodoController(TodoService todoService, UserRepository userRepository){
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<TodoResponseDTO>>> getAllTodos(
                    @RequestParam String username, 
                    HttpServletRequest request) {

        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<TodoResponseDTO> todosDto = todoService.getAllTodos(user);

        return ResponseEntity.ok(
            ApiResponse.success(
                todosDto,
                "Fetched todos successfully",
                path
            )
        );
    }

    @PostMapping("/createTodo")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> createTodo(
                    @RequestBody TodoRequest todoRequest, 
                    @RequestParam String username, 
                    HttpServletRequest request) {

        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TodoResponseDTO todoDto = todoService.createTodo(todoRequest, user);
        
        return ResponseEntity.ok(
            ApiResponse.success(
                todoDto,
                "Todo created successfully",
                path
            )
        );
    }

    @PutMapping("/updateTodo/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> updateTodo(
                    @PathVariable Long todoId, 
                    @RequestBody TodoRequest todoRequest, 
                    @RequestParam String username, 
                    HttpServletRequest request){   
        
        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        TodoResponseDTO todoDto = todoService.updateTodo(todoId, todoRequest, user);
        return ResponseEntity.ok(
            ApiResponse.success(
                todoDto,
                "Todo updated successfully", 
                path
            )
        );
    }

    @DeleteMapping("/deleteTodo/{todoId}")
    public ResponseEntity<ApiResponse<String>> deleteTodo(@PathVariable Long todoId, @RequestParam String username, HttpServletRequest request) {
        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        todoService.deleteTodo(todoId, user);

        return ResponseEntity.ok(
            ApiResponse.success(
                "",
                "Todo deleted successfully",
                path
            )
        );
    }
    
    


}
