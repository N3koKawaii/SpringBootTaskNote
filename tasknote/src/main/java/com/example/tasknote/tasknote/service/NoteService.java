package com.example.tasknote.tasknote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.tasknote.tasknote.exception.ResourceNotFoundException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Note;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.NoteType;
import com.example.tasknote.tasknote.repository.NoteRepository;
import com.example.tasknote.tasknote.requestDto.NoteCreateRequest;
import com.example.tasknote.tasknote.responseDto.NoteResponseDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    // **************
    // DATA RETRIEVAL
    // **************

    // Get all notes for a user
    public List<NoteResponseDTO> getAllNotes(AppUser user){
        return noteRepository.findAllByUser(user)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get a specific note by ID for a user
    public NoteResponseDTO getNoteById(Long id, AppUser user){
        Note note = noteRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        return toResponseDTO(note);
    }

    // List notes by todo
    public List<NoteResponseDTO> listAllNotesByTodo(AppUser user, Todo todo){
        return noteRepository.findAllByUserAndTodo(user, todo)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // List notes by type
    public List<NoteResponseDTO> listAllNotesByType(AppUser user, NoteType type){
        return noteRepository.findAllByUserAndType(user, type)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // **************
    // DATA CREATION
    // **************

    // Create a new note
    public NoteResponseDTO createNote(NoteCreateRequest noteCreateRequest, AppUser user, Todo todo){
        Note note = new Note();
        note.setTitle(noteCreateRequest.getTitle());
        note.setDescription(noteCreateRequest.getDescription());
        note.setType(noteCreateRequest.getType());
        note.setUser(user);
        note.setTodo(todo);
        return toResponseDTO(noteRepository.save(note));
    }

    // Update a note
    public NoteResponseDTO updateNote(Long id, NoteCreateRequest noteCreateRequest, AppUser user, Todo todo){
        Note note = noteRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        
        note.setTitle(noteCreateRequest.getTitle());
        note.setDescription(noteCreateRequest.getDescription());
        note.setType(noteCreateRequest.getTodoId() != null ? NoteType.TODO_NOTE : noteCreateRequest.getType());
        note.setTodo(todo);
        return toResponseDTO(noteRepository.save(note));
    }

    // Delete a note
    public void deleteNote(Long id, AppUser user){
        Note note = noteRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        noteRepository.delete(note);
    }

    // **************
    // DATA CONVERSION
    // **************

    // Convert Note entity to NoteResponseDTO
    public NoteResponseDTO toResponseDTO(Note note){
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setDescription(note.getDescription());
        dto.setType(note.getType());
        dto.setTodoId(note.getTodo() != null ? note.getTodo().getId() : null);
        dto.setCreatedAt(note.getCreatedAt());
        return dto;
    }
}
