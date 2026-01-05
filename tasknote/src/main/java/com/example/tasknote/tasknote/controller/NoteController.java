package com.example.tasknote.tasknote.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.tasknote.tasknote.exception.ResourceNotFoundException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.NoteType;
import com.example.tasknote.tasknote.repository.TodoRepository;
import com.example.tasknote.tasknote.repository.UserRepository;
import com.example.tasknote.tasknote.requestDto.NoteCreateRequest;
import com.example.tasknote.tasknote.responseDto.NoteResponseDTO;
import com.example.tasknote.tasknote.service.NoteService;
import com.example.tasknote.tasknote.service.TodoService;
import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public NoteController(NoteService noteService, TodoRepository todoRepository, UserRepository userRepository){
        this.noteService = noteService;
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getAllNotes(
        @RequestParam String username,
        HttpServletRequest request) {

        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<NoteResponseDTO> notesDto = noteService.getAllNotes(user);

        return ResponseEntity.ok(
            ApiResponse.success(
                notesDto,
                "Fetched notes successfully",
                path
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> getNotebyId(
                            @PathVariable Long id,
                            @RequestParam String username,
                            HttpServletRequest request) {
        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        NoteResponseDTO noteDto = noteService.getNoteById(id, user);

        return ResponseEntity.ok(
            ApiResponse.success(
                noteDto,
                "Fetched note successfully",
                path
            )
        );
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getNotesByTodo(
        @PathVariable Long todoId,
        @RequestParam String username,
        HttpServletRequest request) {

        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                        .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));

        List<NoteResponseDTO> notesDto = noteService.listAllNotesByTodo(user, todo);

        return ResponseEntity.ok(
            ApiResponse.success(
                notesDto,
                "Fetched notes by todo successfully",
                path
            )
        );
    }

    @GetMapping("/{type}")
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getNoteByType(
        @PathVariable NoteType type,
        @RequestParam String username,
        HttpServletRequest request) {
        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<NoteResponseDTO> notesDto = noteService.listAllNotesByType(user, type);

        return ResponseEntity.ok(
            ApiResponse.success(
                notesDto,
                "Fetched notes by type successfully",
                path
            )
        );
    }

    @PostMapping("/createNote")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> createNote(
        @RequestBody NoteCreateRequest noteCreateRequest,
        @RequestParam String username,
        HttpServletRequest request) {

        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Todo todo = null;
        if (noteCreateRequest.getTodoId() != null){
            todo = todoRepository.findByIdAndUser(noteCreateRequest.getTodoId(), user)
                            .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        }

        NoteResponseDTO noteDto = noteService.createNote(noteCreateRequest, user, todo);

        return ResponseEntity.ok(
            ApiResponse.success(
                noteDto,
                "Created note successfully",
                path
            )
        );
    }

    @PutMapping("/updateNote/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> updateNote(
        @PathVariable Long id,
        @RequestBody NoteCreateRequest noteCreateRequest,
        @RequestParam String username,
        HttpServletRequest request) {
        
        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Todo todo = null;
        if (noteCreateRequest.getTodoId() != null){
            todo = todoRepository.findByIdAndUser(noteCreateRequest.getTodoId(), user)
                            .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        }

        NoteResponseDTO noteDto = noteService.updateNote(id, noteCreateRequest, user, todo);

        return ResponseEntity.ok(
            ApiResponse.success(
                noteDto,
                "Updated note successfully",
                path
            )
        );
    }

    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNote(
        @PathVariable Long id,
        @RequestParam String username,
        HttpServletRequest request
    ){
        String path = request.getRequestURI();

        AppUser user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        noteService.deleteNote(id, user);

        return ResponseEntity.ok(
            ApiResponse.success(
                "",
                "Note deleted successfully",
                path
            )
        );

    }
}