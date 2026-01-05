package com.example.tasknote.tasknote.model;

import java.util.Optional;

import com.example.tasknote.tasknote.model_enum.NoteType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="note")
public class Note extends BaseEntity{
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private NoteType type;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="todo_id")
    private Todo todo;

    public Note(){
        super();
    }

    public Note(String title, String description, Todo todo, NoteType type, AppUser user) {
        super();
        this.title = title;
        this.description = description;
        this.todo = todo;
        this.type = type;
        this.user = user;
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

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Note{");
        sb.append("title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", type=").append(type);
        sb.append(", user=").append(user);
        sb.append(", todo=").append(todo);
        sb.append('}');
        return sb.toString();
    }
}
