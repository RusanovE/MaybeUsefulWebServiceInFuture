package com.example.mushroomsdetect.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "universities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class University {
    @Id
    @Column(nullable = false,unique = true)
    private String title;

    @Transient
    private String url;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String place;

}