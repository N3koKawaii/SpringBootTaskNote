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

@Entity
@Table(name="todo")
public class Todo extends BaseEntity{
    @Column(nullable=false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Priority priority = Priority.LOW;

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    
    private LocalDateTime deleted_at;

    
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

    public Todo(String title, String description, AppUser user){
        super();
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public Todo(String title, String description, LocalDateTime end_date, Priority priority, LocalDateTime start_date, Status status, LocalDateTime deleted_at, AppUser user) {
        super();
        this.title = title;
        this.description = description;
        this.end_date = end_date;
        this.priority = priority;
        this.start_date = start_date;
        this.status = status;
        this.deleted_at = deleted_at;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public AppUser getUser() {
        return user;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public void setUser(AppUser user) {
        this.user = user;
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
        sb.append(", start_date=").append(start_date);
        sb.append(", end_date=").append(end_date);
        sb.append(", deleted_at=").append(deleted_at);
        sb.append(", user=").append(user);
        sb.append(", notes=").append(notes);
        sb.append('}');
        return sb.toString();
    }

    @PrePersist
    protected void onCreate() {
        if(getStart_date() == null){
            setStart_date(LocalDateTime.now());
        }
        if(getEnd_date() == null){
            setEnd_date(getStart_date().plusDays(7));
        }
        
    }

    
    
}
