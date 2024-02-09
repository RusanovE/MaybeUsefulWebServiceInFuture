package com.example.mushroomsdetect.services;

import com.example.mushroomsdetect.entitys.CachedUser;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CachedUserService {
    CachedUser findUserBy(long userId);

    void addNewCachedUser(CachedUser cachedUser);

    List<CachedUser> getCachedUserList();
    @Transactional
    void deleteUser(long userId);
}
