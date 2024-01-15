package com.example.mushroomsdetect.controllers;

import com.example.mushroomsdetect.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    final UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model){

        model.addAttribute("users",userService.getUserList());

        return "admin-panel";
    }

    @PostMapping("/admin/banUser/{userId}")
    public String banUser(@PathVariable long userId){
        userService.updateUserDetails(userId, false);

        log.info("User with id(" + userId + ")was baned");
        return "redirect:/admin";
    }

    @PostMapping("/admin/unbanUser/{userId}")
    public String unbanUser(@PathVariable long userId){
        userService.updateUserDetails(userId, true);

        log.info("User with id(" + userId + ")was unbaned");
        return "redirect:/admin";
    }
}
