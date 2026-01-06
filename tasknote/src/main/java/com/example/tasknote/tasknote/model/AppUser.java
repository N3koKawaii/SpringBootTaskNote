package com.example.tasknote.tasknote.model;

import java.util.List;

import com.example.tasknote.tasknote.model_enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name="app_user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames="username"),
        @UniqueConstraint(columnNames="email")
    }
)
public class AppUser extends BaseEntity{
    @Column(unique=true, nullable=false)
    private String username;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean enabled = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Todo> todos;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Note> notes;

    public AppUser(){
        super();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AppUser{");
        sb.append("username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", role=").append(role);
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    
}
