package com.example.tasknote.tasknote.requestDto;

import java.time.LocalDateTime;

import com.example.tasknote.tasknote.model_enum.Priority;
import com.example.tasknote.tasknote.model_enum.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TodoRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Priority priority = Priority.LOW;

    @NotNull
    private Status status = Status.PENDING;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    
}
