package com.example.mushroomsdetect.controllers;

import com.example.mushroomsdetect.DTO.UserDTO;
import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationAndAuthorizationController {

    final UserService userService;

    final PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registrationForm(){
        return "registration";
    }

    @PostMapping("/processRegistration")
    public String registerNewUser(UserDTO userDTO){

        try {
            UserOfApp user = new UserOfApp(userDTO.getLogin(),passwordEncoder.encode(userDTO.getPassword()),userDTO.getRole(),true);
            if (userService.registerNewUser(user,userDTO.getPhoto())){
                log.info("New user registered successfully");
                return "redirect:/welcome";
            }  else return "redirect:/registration";
        } catch (Exception e) {
            log.error("Error with operation: \"Register user\"  - " + e.getMessage());
            return "redirect:/error/404";
        }
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/logout")
    public String logoutForm(){
        return "logout";
    }


}
