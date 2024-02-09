package com.example.mushroomsdetect;

import com.example.mushroomsdetect.DTO.UserDTO;
import com.example.mushroomsdetect.controllers.UserController;
import com.example.mushroomsdetect.entitys.Role;
import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.services.UserService;
import com.example.mushroomsdetect.services.impl.CustomUserDetailServiceImpl;
import com.example.mushroomsdetect.utill.ImageConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CustomUserDetailServiceImpl userDetailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserController userController;

    @Test
    void testWelcome() {
        // Arrange
        List<UserOfApp> userList = List.of(new UserOfApp("user1", "password1", Role.ROLE_USER, true));
        Mockito.when(userService.getActiveUserList()).thenReturn(userList);

        // Act
        String result = userController.welcome(model);

        // Assert
        assertEquals("welcome", result);
        verify(model).addAttribute("userList", userList);
    }

    @Test
    void testGetUserProfile() {
        // Arrange
        String username = "user1";
        UserOfApp user = new UserOfApp(username, "password1", Role.ROLE_USER, true);
        Mockito.when(principal.getName()).thenReturn(username);
        Mockito.when(userService.findUserBy(username)).thenReturn(user);

        // Act
        String result = userController.getUserProfile(model, principal);

        // Assert
        assertEquals("user-profile", result);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("userPhotoBase64", ImageConverter.convertBytesToBase64(user.getUserPhoto()));
    }

    @Test
    void testChangeUserDetails() {
        // Arrange
        String login = "newUser";
        String password = "newPassword";
        String encodedPassword = "encodedPassword";
        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Act
        String result = userController.changeUserDetails(UserDTO.builder()
                .login(login)
                .password(password)
                .build(), principal);

        // Assert
        assertEquals("redirect:/userProfile", result);
        verify(userService).updateUserDetails(principal.getName(), login, encodedPassword, true);
        verify(userDetailService).reloadUserByUsername(login, principal);
    }
}
