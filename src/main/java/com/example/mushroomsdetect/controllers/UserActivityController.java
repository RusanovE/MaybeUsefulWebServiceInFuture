package com.example.mushroomsdetect.controllers;

import com.example.mushroomsdetect.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class UserActivityController {

    private final UserService userService;

    @PostMapping("/updateUserActivity")
    public ResponseEntity<String> updateUserActivity(HttpSession session, Principal principal) {
        // Получение логина пользователя из вашей системы аутентификации
        String username = principal.getName();

        // Проверка, что логин не пустой (или другие проверки)
        if (username != null && !username.isEmpty()) {
            // Обновление времени последнего действия пользователя в сессии
            session.setAttribute("lastActivityTime", System.currentTimeMillis());

            // Обновление статуса активности пользователя в базе данных
            userService.updateUserDetails(username,"", "", true);

            return ResponseEntity.ok("User activity updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }
    }

}
