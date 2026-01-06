package com.example.tasknote.tasknote.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tasknote.tasknote.exception.ResourceNotFoundException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.Priority;
import com.example.tasknote.tasknote.model_enum.Status;
import com.example.tasknote.tasknote.repository.TodoRepository;

import com.example.tasknote.tasknote.requestDto.TodoRequest;
import com.example.tasknote.tasknote.responseDto.TodoResponseDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TodoService {

    private final NoteService noteService;
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository, NoteService noteService) {
        this.todoRepository = todoRepository;
        this.noteService = noteService;
    }

    // **************
    // Data Retrieval
    // **************

    // Get all todos for a user
    public List<TodoResponseDTO> getAllTodos(AppUser user){
        return todoRepository.findAllByUserWithNotes(user)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get a specific todo by ID for a user
    public TodoResponseDTO getTodoById(Long id, AppUser user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                        .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        return toResponseDTO(todo);
    }

    // List todos by status
    public List<TodoResponseDTO> listAllTodosByStatus(AppUser user, Status status){
        return todoRepository.findAllByUserAndStatus(user, status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // List todos by priority
    public List<TodoResponseDTO> listAllTodosByPriority(AppUser user, Priority priority){
        return todoRepository.findAllByUserAndPriority(user, priority)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }




    
    // **************
    // Data Management
    // **************

    // Create a new todo
    public TodoResponseDTO createTodo(TodoRequest todoRequest, AppUser user){
        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setPriority(todoRequest.getPriority() != null ? todoRequest.getPriority() : Priority.LOW);
        todo.setStatus(todoRequest.getStatus() != null ? todoRequest.getStatus() : Status.PENDING);
        todo.setStartAt(todoRequest.getStartDate());
        todo.setEndAt(todoRequest.getEndDate());
        todo.setUser(user);

        return toResponseDTO(todoRepository.save(todo));
    }

    // Update an existing todo
    public TodoResponseDTO updateTodo(Long id, TodoRequest todoRequest, AppUser user){
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
    
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setPriority(todoRequest.getPriority());
        if (todoRequest.getStatus() != null) {
            todo.setStatus(todoRequest.getStatus());
        }
        if (todoRequest.getStartDate() != null) {
            todo.setStartAt(todoRequest.getStartDate());
        }
        if (todoRequest.getEndDate() != null) {
            todo.setEndAt(todoRequest.getEndDate());
        }

        return toResponseDTO(todo);
    }

    // Delete a todo
    public void deleteTodo(Long id, AppUser user){
        if (!todoRepository.existsByIdAndUser(id, user)) {
            throw new ResourceNotFoundException("Todo not found");
        }
        todoRepository.deleteById(id);
    }




    
    // **************
    // DATA CONVERSION
    // **************

    // Convert Todo entity to TodoResponseDTO
    public TodoResponseDTO toResponseDTO(Todo todo){
        TodoResponseDTO dto = new TodoResponseDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus());
        dto.setPriority(todo.getPriority());
        dto.setStartDate(todo.getStartAt());
        dto.setEndDate(todo.getEndAt());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        dto.setNotes(
            todo.getNotes()
                .stream()
                .map(note -> noteService.toResponseDTO(note))
                .toList());
        return dto;
    }


}
