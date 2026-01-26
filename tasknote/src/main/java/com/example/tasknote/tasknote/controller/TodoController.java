package com.example.tasknote.tasknote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.requestDto.TodoRequest;
import com.example.tasknote.tasknote.responseDto.TodoResponseDTO;
import com.example.tasknote.tasknote.service.CurrentUserService;
import com.example.tasknote.tasknote.service.TodoService;
import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/todos/")
public class TodoController {
    private final TodoService todoService;
    private final CurrentUserService currentUserService;

    public TodoController(TodoService todoService,CurrentUserService currentUserService) {
        this.todoService = todoService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TodoResponseDTO>>> getAllTodos() {
     
        AppUser user = currentUserService.getCurrentUser();

        List<TodoResponseDTO> todosDto = todoService.getAllTodos(user);

        return ResponseEntity.ok(
            ApiResponse.success(
                todosDto,
                "Fetched todos successfully"
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TodoResponseDTO>> createTodo(@Valid @RequestBody TodoRequest todoRequest) {

        AppUser user = currentUserService.getCurrentUser();

        TodoResponseDTO todoDto = todoService.createTodo(todoRequest, user);
        
        return ResponseEntity.ok(
            ApiResponse.success(
                todoDto,
                "Todo created successfully"
            )
        );
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>> updateTodo(
                    @PathVariable Long todoId, 
                    @Valid @RequestBody TodoRequest todoRequest){   

        AppUser user = currentUserService.getCurrentUser();
        TodoResponseDTO todoDto = todoService.updateTodo(todoId, todoRequest, user);
        return ResponseEntity.ok(
            ApiResponse.success(
                todoDto,
                "Todo updated successfully"
            )
        );
    }

    @DeleteMapping("/{todoId}")
    @PreAuthorize("hasRole('ADMIN') or @todoService.isOwner(#todoId, principal.username)")
    public ResponseEntity<ApiResponse<String>> deleteTodo(@PathVariable Long todoId) {

        AppUser user = currentUserService.getCurrentUser();
        todoService.deleteTodo(todoId, user);

        return ResponseEntity.ok(
            ApiResponse.success(
                "",
                "Todo deleted successfully"
            )
        );
    }
    
    


}
