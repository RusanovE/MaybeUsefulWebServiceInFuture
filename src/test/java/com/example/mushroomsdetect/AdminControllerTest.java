package com.example.mushroomsdetect;

import com.example.mushroomsdetect.controllers.AdminController;
import com.example.mushroomsdetect.entitys.CachedUser;
import com.example.mushroomsdetect.entitys.Role;
import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.services.CachedUserService;
import com.example.mushroomsdetect.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CachedUserService cachedUserService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testAdminPage() {
        // Arrange
        Model model = Mockito.mock(Model.class);
        List<UserOfApp> userList = userService.getUserList();
        List<CachedUser> cachedUserList = cachedUserService.getCachedUserList();
        Mockito.when(userService.getUserList()).thenReturn(userList);
        Mockito.when(cachedUserService.getCachedUserList()).thenReturn(cachedUserList);

        // Act
        String result = adminController.adminPage(model);

        // Assert
        assertEquals("admin-panel", result);
        Mockito.verify(model).addAttribute("users", userList);
        Mockito.verify(model).addAttribute("cachedUsers", cachedUserList);

        System.out.println("First test completed!");
    }

    @Test
    void testBanUser() {
        // Arrange
        long userId = 1L;

        // Act
        String result = adminController.banUser(userId);

        // Assert
        assertEquals("redirect:/admin", result);
        Mockito.verify(userService).updateUserDetails(userId, false);

        System.out.println("Second test completed!");
    }

    @Test
    void testUnbanUser() {
        // Arrange
        long userId = 1L;

        // Act
        String result = adminController.unbanUser(userId);

        // Assert
        assertEquals("redirect:/admin", result);
        Mockito.verify(userService).updateUserDetails(userId, true);

        System.out.println("Third test completed!");
    }

    @Test
    void testDeleteUser() {
        // Arrange
        long userId = 1L;
        UserOfApp user = new UserOfApp("user1", "password1", Role.ROLE_USER, true);
        Mockito.when(userService.findUserBy(userId)).thenReturn(user);

        // Act
        String result = adminController.deleteUser(userId);

        // Assert
        assertEquals("redirect:/admin", result);
        Mockito.verify(cachedUserService).addNewCachedUser(any(CachedUser.class));
        Mockito.verify(userService).deleteUser(userId);

        System.out.println("Forth test completed!");
    }

    @Test
    void testRestoreUser() {
        // Arrange
        long userId = 1L;
        CachedUser cachedUser = new CachedUser("user1", "password1", Role.ROLE_USER);
        Mockito.when(cachedUserService.findUserBy(userId)).thenReturn(cachedUser);

        // Act
        String result = adminController.restoreUser(userId);

        // Assert
        assertEquals("redirect:/admin", result);
        Mockito.verify(userService).registerNewUser(any(UserOfApp.class), isNull());
        Mockito.verify(cachedUserService).deleteUser(userId);

        System.out.println("Fifth test completed!");
    }

}