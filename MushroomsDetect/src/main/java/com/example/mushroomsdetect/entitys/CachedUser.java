package com.example.mushroomsdetect.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CachedUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String login;

    @Column(nullable = false, unique = true)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = false, columnDefinition = "boolean default false")
    boolean isActive;

    public CachedUser(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}