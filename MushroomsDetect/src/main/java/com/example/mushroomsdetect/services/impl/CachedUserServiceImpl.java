package com.example.mushroomsdetect.services.impl;

import com.example.mushroomsdetect.entitys.CachedUser;
import com.example.mushroomsdetect.repos.CachedUserRepo;
import com.example.mushroomsdetect.services.CachedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CachedUserServiceImpl implements CachedUserService {

    final CachedUserRepo cachedUserRepo;

    @Override
    public CachedUser findUserBy(long userId) {
        return cachedUserRepo.findById(userId);
    }


    @Override
    public void addNewCachedUser(CachedUser cachedUser) {
        cachedUserRepo.save(cachedUser);
    }

    @Override
    public List<CachedUser> getCachedUserList() {
        return cachedUserRepo.findAll();
    }

    @Override
    public void deleteUser(long userId) {
        cachedUserRepo.deleteById(userId);
    }
}
