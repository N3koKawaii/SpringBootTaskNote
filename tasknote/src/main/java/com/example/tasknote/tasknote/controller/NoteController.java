package com.example.tasknote.tasknote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.tasknote.tasknote.exception.ResourceNotFoundException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.NoteType;
import com.example.tasknote.tasknote.repository.TodoRepository;
import com.example.tasknote.tasknote.requestDto.NoteCreateRequest;
import com.example.tasknote.tasknote.responseDto.NoteResponseDTO;
import com.example.tasknote.tasknote.service.CurrentUserService;
import com.example.tasknote.tasknote.service.NoteService;
import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/notes/")
public class NoteController {
    private final NoteService noteService;
    private final TodoRepository todoRepository;
    private final CurrentUserService currentUserService;

    public NoteController(NoteService noteService, TodoRepository todoRepository, CurrentUserService currentUserService){
        this.noteService = noteService;
        this.todoRepository = todoRepository;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getAllNotes() {

        AppUser user = currentUserService.getCurrentUser();

        List<NoteResponseDTO> notesDto = noteService.getAllNotes(user);

        return ResponseEntity.ok(
            ApiResponse.success(
                notesDto,
                "Fetched notes successfully"
            )
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> getNotebyId(@PathVariable Long id) {

        AppUser user = currentUserService.getCurrentUser();
        NoteResponseDTO noteDto = noteService.getNoteById(id, user);

        return ResponseEntity.ok(
            ApiResponse.success(
                noteDto,
                "Fetched note successfully"
            )
        );
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getNoteByType(@PathVariable NoteType type) {

        AppUser user = currentUserService.getCurrentUser();
        List<NoteResponseDTO> notesDto = noteService.listAllNotesByType(user, type);

        return ResponseEntity.ok(
            ApiResponse.success(
                notesDto,
                "Fetched notes by type successfully"
            )
        );
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<ApiResponse<List<NoteResponseDTO>>> getNotesByTodo(@PathVariable Long todoId) {

        AppUser user = currentUserService.getCurrentUser();

        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                        .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));

        List<NoteResponseDTO> notesDto = noteService.listAllNotesByTodo(user, todo);

        return ResponseEntity.ok(
            ApiResponse.success(
                notesDto,
                "Fetched notes by todo successfully"
            )
        );
    }

    

    @PostMapping
    public ResponseEntity<ApiResponse<NoteResponseDTO>> createNote(@Valid @RequestBody NoteCreateRequest noteCreateRequest) {

        AppUser user = currentUserService.getCurrentUser();

        Todo todo = null;
        if (noteCreateRequest.getTodoId() != null){
            todo = todoRepository.findByIdAndUser(noteCreateRequest.getTodoId(), user)
                            .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        }

        NoteResponseDTO noteDto = noteService.createNote(noteCreateRequest, user, todo);

        return ResponseEntity.ok(
            ApiResponse.success(
                noteDto,
                "Created note successfully"
            )
        );
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponse<NoteResponseDTO>> updateNote(
        @PathVariable Long noteId,
        @Valid @RequestBody NoteCreateRequest noteCreateRequest) {

        AppUser user = currentUserService.getCurrentUser();

        Todo todo = null;
        if (noteCreateRequest.getTodoId() != null){
            todo = todoRepository.findByIdAndUser(noteCreateRequest.getTodoId(), user)
                            .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        }

        NoteResponseDTO noteDto = noteService.updateNote(noteId, noteCreateRequest, user, todo);

        return ResponseEntity.ok(
            ApiResponse.success(
                noteDto,
                "Updated note successfully"
            )
        );
    }

    @DeleteMapping("/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or @noteService.isOwner(#noteId, principal.username)")
    public ResponseEntity<ApiResponse<String>> deleteNote(@PathVariable Long noteId){

        AppUser user = currentUserService.getCurrentUser();
        noteService.deleteNote(noteId, user);

        return ResponseEntity.ok(
            ApiResponse.success(
                "",
                "Note deleted successfully"
            )
        );

    }
}