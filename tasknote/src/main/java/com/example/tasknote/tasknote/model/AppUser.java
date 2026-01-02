package com.example.tasknote.tasknote.model;

import com.example.tasknote.tasknote.model_enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    public AppUser(){
        super();
    }

    public AppUser(String email, String password, Role role, String username) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
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
        sb.append(", password=").append(password);
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
}
