package com.example.mushroomsdetect.services;

import com.example.mushroomsdetect.entitys.UserOfApp;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    @Transactional
    boolean registerNewUser(UserOfApp user, MultipartFile photo);

/*    public boolean identificationUser(String login, String password){
        return userRepo.searchByLoginAndPassword(login, password);
    }*/

    UserOfApp findUserBy(String login);

/*
    UserOfApp findUserBy(String login, String password);*/

    List<UserOfApp> getUserList();


    List<UserOfApp> getActiveUserList();

    void updateUserDetails(long userId, boolean status);

    void updateUserDetails(String login, String newLogin, String newPassword, boolean status);

    void deleteUser(long userId);

    UserOfApp findUserBy(long userId);
}
