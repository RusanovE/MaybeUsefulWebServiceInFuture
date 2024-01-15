package com.example.mushroomsdetect.repos;

import com.example.mushroomsdetect.entitys.UserOfApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<UserOfApp,Long> {
    UserOfApp findByLogin(String login);

    UserOfApp findById(long id);

    boolean searchByLoginAndPassword(String login, String password);

//    UserOfApp findByLoginAndPassword(String login, String password);
}
