package com.example.mushroomsdetect;

import com.example.mushroomsdetect.entitys.Role;
import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.repos.UserRepo;
import com.example.mushroomsdetect.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepository;

    @Mock
    private MultipartFile multipartFile;

    @Test
    void testRegisterNewUser() {
        // Arrange
        UserOfApp user = new UserOfApp("user1", "password1", Role.ROLE_USER, true);
        Mockito.when(userRepository.save(any(UserOfApp.class))).thenReturn(user);

        // Act
        boolean result = userService.registerNewUser(user, multipartFile);

        // Assert
        assertTrue(result);
    }

    @Test
    void testFindUserByLogin() {
        // Arrange
        String login = "user1";
        UserOfApp user = new UserOfApp(login, "password1", Role.ROLE_USER, true);
        Mockito.when(userRepository.findByLogin(login)).thenReturn(user);

        // Act
        UserOfApp result = userService.findUserBy(login);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetUserList() {
        // Arrange
        List<UserOfApp> userList = Arrays.asList(
                new UserOfApp("user1", "password1", Role.ROLE_USER, true),
                new UserOfApp("user2", "password2", Role.ROLE_USER, true)
        );
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserOfApp> result = userService.getUserList();

        // Assert
        assertEquals(userList, result);
    }

    @Test
    void testGetActiveUserList() {
        // Arrange
        List<UserOfApp> activeUserList = Arrays.asList(
                new UserOfApp("user1", "password1", Role.ROLE_USER, true),
                new UserOfApp("user2", "password2", Role.ROLE_USER, true)
        );
        Mockito.when(userRepository.findAllByIsActiveTrue()).thenReturn(activeUserList);

        // Act
        List<UserOfApp> result = userService.getActiveUserList();

        // Assert
        assertEquals(activeUserList, result);
    }

    @Test
    void testUpdateUserDetails() {
        // Arrange
        long userId = 1L;
        boolean status = false;
        UserOfApp user = new UserOfApp("user1", "password1", Role.ROLE_USER, true);
        Mockito.when(userRepository.findById(userId)).thenReturn(user);

        // Act
        userService.updateUserDetails(userId, status);

        // Assert
        assertEquals(status, user.isActive());
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserDetailsWithLogin() {
        // Arrange
        String login = "user1";
        String newLogin = "newUser1";
        String newPassword = "newPassword1";
        boolean status = false;

        UserOfApp user = new UserOfApp(login, "password1", Role.ROLE_USER, true);
        Mockito.when(userRepository.findByLogin(login)).thenReturn(user);

        // Act
        userService.updateUserDetails(login, newLogin, newPassword, status);

        // Assert
        assertEquals(newLogin, user.getLogin());
        assertEquals(newPassword, user.getPassword());
        assertEquals(status, user.isActive());
    }

    @Test
    void testDeleteUser() {
        // Arrange
        long userId = 1L;
        UserOfApp user = new UserOfApp("user1", "password1", Role.ROLE_USER, true);

        // Allow this stubbing to be unnecessary
        Mockito.lenient().when(userRepository.findById(userId)).thenReturn(user);

        // Act
        userService.deleteUser(userId);

        // Assert
        Mockito.verify(userRepository).deleteById(userId);
    }
}