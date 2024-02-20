package com.example.mushroomsdetect.services;

import com.example.mushroomsdetect.entitys.University;
import org.springframework.data.domain.Page;

public interface UniversityService {

    Page<University> getUniversities(int page, int size, String sortBy, String sortDir);

    void setUniversities();
}
