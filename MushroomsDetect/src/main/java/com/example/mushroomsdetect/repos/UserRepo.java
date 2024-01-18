package com.example.mushroomsdetect.repos;

import com.example.mushroomsdetect.entitys.UserOfApp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo  extends JpaRepository<UserOfApp,Long> {
    @Transactional
    UserOfApp findByLogin(String login);

    @Transactional
    UserOfApp findById(long id);

    boolean searchByLoginAndPassword(String login, String password);

    UserOfApp findByLoginAndPassword(String login, String password);
}
