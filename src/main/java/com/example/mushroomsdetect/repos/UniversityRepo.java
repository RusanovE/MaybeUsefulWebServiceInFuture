package com.example.mushroomsdetect.repos;

import com.example.mushroomsdetect.entitys.University;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepo extends JpaRepository<University, String> {

    @Override
    Page<University> findAll(@NonNull Pageable pageable);
}
