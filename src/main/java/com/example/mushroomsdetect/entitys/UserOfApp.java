package com.example.mushroomsdetect.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserOfApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String login;

    @Column(nullable = false,unique = true)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @Lob
    byte[] userPhoto;

    @Column(nullable = false)
    boolean isActive;

    public UserOfApp(String login, String password, Role role, boolean isActive) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    @Override
    public String toString(){
        return String.format("login = %s, role = %s, status = %s ", login, role, isActive);
    }

}
