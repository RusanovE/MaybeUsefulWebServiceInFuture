package com.example.mushroomsdetect.repos;

import com.example.mushroomsdetect.entitys.CachedUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachedUserRepo extends JpaRepository<CachedUser,Long> {

    @Transactional
    CachedUser findById(long id);
}
