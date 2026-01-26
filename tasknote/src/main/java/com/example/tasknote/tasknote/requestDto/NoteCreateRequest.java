package com.example.tasknote.tasknote.requestDto;

import com.example.tasknote.tasknote.model_enum.NoteType;

import jakarta.validation.constraints.NotBlank;

public class NoteCreateRequest {
    @NotBlank
    private String title;
    private String description;

    // optional
    private Long todoId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getTodoId() {
        return todoId;
    }

}
