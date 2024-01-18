package com.example.mushroomsdetect.controllers;

import com.example.mushroomsdetect.entitys.CachedUser;
import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.services.CachedUserService;
import com.example.mushroomsdetect.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    final UserService userService;

    final CachedUserService cachedUserService;

    @GetMapping
    public String adminPage(Model model){
        try {
            model.addAttribute("users",userService.getUserList());
            model.addAttribute("cachedUsers", cachedUserService.getCachedUserList());

            return "admin-panel";
        } catch (Exception e) {
            log.error("Error with operation: \"Show admin page\"  - " + e.getMessage());
            return "redirect:/error/404";
        }

    }

    @PostMapping("/banUser/{userId}")
    public String banUser(@PathVariable long userId) {
        try {
            userService.updateUserDetails(userId, false);

            log.info("User with id(" + userId + ")was banned");
            return "redirect:/admin";

        } catch (Exception e) {
            log.error("Error with operation: \"Ban user\"  - " + e.getMessage());
            return "redirect:/error/404";
        }
    }

    @PostMapping("/unbanUser/{userId}")
    public String unbanUser(@PathVariable long userId){
        try {
            userService.updateUserDetails(userId, true);

            log.info("User with id(" + userId + ")was unbanned");
            return "redirect:/admin";

        } catch (Exception e) {
            log.error("Error with operation: \"Unban user\"  - " + e.getMessage());
            return "redirect:/error/404";
        }

    }

    @PostMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable long userId){
        try {
            UserOfApp user = userService.findUserBy(userId);
            CachedUser cachedUser = new CachedUser(user.getLogin(),user.getPassword(),user.getRole());
            cachedUserService.addNewCachedUser(cachedUser);

            userService.deleteUser(userId);
            log.info("User with id(" + userId + ")was deleted");
            return "redirect:/admin";
        } catch (Exception e) {
            log.error("Error with operation: \"Delete user\"  - " + e.getMessage());
            return "redirect:/error/404";
        }
    }

    @PostMapping("/restoreUser/{userId}")
    public String restoreUser(@PathVariable long userId){
        try {
            CachedUser user = cachedUserService.findUserBy(userId);
            UserOfApp restoreUser = new UserOfApp(user.getLogin(),user.getPassword(),user.getRole(),true);
            userService.registerNewUser(restoreUser,null);

            cachedUserService.deleteUser(userId);
            log.info("User with id(" + userId + ")was restored");
            return "redirect:/admin";
        } catch (Exception e) {
            log.error("Error with operation: \"Restore user\"  - " + e.getMessage());
            return "redirect:/error/404";
        }
    }



}
