package com.example.mushroomsdetect.controllers;

import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.services.UserService;
import com.example.mushroomsdetect.services.impl.CustomUserDetailServiceImpl;
import com.example.mushroomsdetect.utill.ImageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    final UserService userService;

    final CustomUserDetailServiceImpl userDetailService;

    final PasswordEncoder passwordEncoder;

    @RequestMapping({"/","/welcome"})
    public String welcome(Model model) {

        List<UserOfApp> userList = userService.getUserList();

        // Передаем список в модель
        model.addAttribute("userList", userList);

        // Возвращаем имя представления (HTML-шаблона)
        return "welcome";
    }

    @GetMapping("/userProfile")
    public String getUserProfile(Model model, Principal principal){

        UserOfApp user = userService.findUserBy(principal.getName());

        String userPhotoBase64 = ImageConverter.convertBytesToBase64(user.getUserPhoto());
        model.addAttribute("userPhotoBase64", userPhotoBase64);
        model.addAttribute("user", user);

        return "user-profile";
    }

    @PostMapping("/userProfile/changeUserDetails")
    public String changeUserDetails(@RequestParam String login,
                                    @RequestParam String password,
                                    Principal principal) {
        try {
            userService.updateUserDetails(principal.getName(), login, passwordEncoder.encode(password));

            userDetailService.reloadUserByUsername(login,principal);

            log.info("User data changed");
            return "redirect:/userProfile";
        } catch (Exception e) {
            log.error("Error changing user data: "+ e.getMessage());
            return "redirect:error/404";
        }
    }
}
