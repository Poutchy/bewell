package com.example.authservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(nullable = false)
    private String role;

    private boolean fromGoogle;

    public User(Long id, String username, String password, String role, boolean fromGoogle) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fromGoogle = fromGoogle;
    }

    public User() { }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isFromGoogle() { return fromGoogle; }
}

