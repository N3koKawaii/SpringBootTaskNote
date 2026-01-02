package com.example.tasknote.tasknote.requestDto;

import com.example.tasknote.tasknote.model_enum.NoteType;

public class NoteCreateRequest {
    private String title;
    private String description;
    private NoteType type = NoteType.STANDALONE;

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

    public NoteType getType() {
        return type;
    }
}
