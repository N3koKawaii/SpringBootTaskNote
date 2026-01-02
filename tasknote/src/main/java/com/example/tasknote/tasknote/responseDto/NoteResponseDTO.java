package com.example.tasknote.tasknote.responseDto;

import java.time.LocalDateTime;

import com.example.tasknote.tasknote.model_enum.NoteType;

public class NoteResponseDTO {
    private Long id;
    private String title;
    private String description;
    private NoteType type;
    private Long todoId;
    private LocalDateTime createdAt;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public NoteType getType() {
        return type;
    }
    public void setType(NoteType type) {
        this.type = type;
    }
    public Long getTodoId() {
        return todoId;
    }
    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
}
