package com.example.mushroomsdetect.services.impl;

import com.example.mushroomsdetect.entitys.University;
import com.example.mushroomsdetect.repos.UniversityRepo;
import com.example.mushroomsdetect.services.UniversityScrapingService;
import com.example.mushroomsdetect.services.UniversityService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    final UniversityRepo universityRepo;

    final UniversityScrapingService universityScrapingService;

    @PostConstruct
    public void init() {
        setUniversities();
    }
    public Page<University> getUniversities(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return universityRepo.findAll(pageable);
    }

    @Override
    public void setUniversities(){
        List<University> universities = universityScrapingService.scrapeUniversityTitles();

        universityRepo.saveAll(universities);
    }
}


