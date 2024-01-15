package com.example.mushroomsdetect.controllers;

import com.example.mushroomsdetect.entitys.Role;
import com.example.mushroomsdetect.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    //TODO Переделать на DTO
    @PostMapping("/processRegistration")
    public String registerNewUser(@RequestParam String login,
                                  @RequestParam String password,
                                  @RequestParam Role role,
                                  @RequestParam MultipartFile photo){

        if (userService.registerNewUser(login,passwordEncoder.encode(password),role,photo)){
            log.info("New user registered successfully");
            return "redirect:/welcome";
        }
        else return "redirect:/registration";
    }

/*    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/logout")
    public String logoutForm(){
        return "logout";
    }*/


}
