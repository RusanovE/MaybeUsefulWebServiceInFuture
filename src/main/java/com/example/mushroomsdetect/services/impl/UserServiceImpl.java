package com.example.mushroomsdetect.services.impl;

import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.repos.UserRepo;
import com.example.mushroomsdetect.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public boolean registerNewUser(UserOfApp user, MultipartFile photo) {
        try {

            if (photo != null && !photo.isEmpty()){
                user.setUserPhoto(photo.getBytes());
                userRepo.save(user);
            }else userRepo.save(user);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserOfApp findUserBy(String login){ return userRepo.findByLogin(login);}

    @Override
    public List<UserOfApp> getUserList(){ return userRepo.findAll();}

    @Override
    @Transactional
    public List<UserOfApp> getActiveUserList() {
        return userRepo.findAllByIsActiveTrue();
    }

    @Override
    @Transactional
    public void updateUserDetails(long userId, boolean status) {
        UserOfApp user = userRepo.findById(userId);

        user.setActive(status);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void updateUserDetails(String login, String newLogin, String newPassword, boolean status) {
        UserOfApp user = userRepo.findByLogin(login);

        if (!newLogin.isEmpty()) user.setLogin(newLogin);
        if (!newPassword.isEmpty()) user.setPassword(newPassword);
        user.setActive(status);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public UserOfApp findUserBy(long userId) {
        return userRepo.findById(userId);
    }

}
