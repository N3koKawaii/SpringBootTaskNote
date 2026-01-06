package com.example.tasknote.tasknote.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.tasknote.tasknote.model_enum.Priority;
import com.example.tasknote.tasknote.model_enum.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="todo")
public class Todo extends BaseEntity{
    @Column(nullable=false)
    @NotBlank
    @Size(max=255)
    private String title;

    @Size(max = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Priority priority = Priority.LOW;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;

    @OneToMany(
        mappedBy="todo",
        cascade=CascadeType.ALL,
        orphanRemoval=true
    )
    private List<Note> notes = new ArrayList<>();

    public Todo(){
        super();
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Todo{");
        sb.append("title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", priority=").append(priority);
        sb.append(", startAt=").append(startAt);
        sb.append(", endAt=").append(endAt);
        sb.append('}');
        return sb.toString();
    }

    @PrePersist
    protected void onCreate() {
        if(getStartAt() == null){
            setStartAt(LocalDateTime.now());
        }
        if(getEndAt() == null){
            setEndAt(getStartAt().plusDays(7));
        }
        
    }

    public void addNote(Note note) {
        notes.add(note);
        note.setTodo(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setTodo(null);
    }

    
    
}
