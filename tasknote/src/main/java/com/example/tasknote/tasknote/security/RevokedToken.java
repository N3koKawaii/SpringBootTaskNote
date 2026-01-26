package com.example.tasknote.tasknote.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "revoked_token")
public class RevokedToken {

    @Id
    private String jti;

    @Column(nullable = false)
    private long expiresAt;

    @Column(nullable = true)
    private String username;

    public RevokedToken() {
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}