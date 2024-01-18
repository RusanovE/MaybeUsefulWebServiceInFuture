package com.example.mushroomsdetect.services;

import com.example.mushroomsdetect.entitys.Role;
import com.example.mushroomsdetect.entitys.UserOfApp;
import com.example.mushroomsdetect.repos.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    public boolean registerNewUser(String login, String password, Role role, MultipartFile photo) {

        try {
            UserOfApp user = new UserOfApp(login,password,role,true);

            if (photo != null && !photo.isEmpty()){
                user.setUserPhoto(photo.getBytes());
            }else return false;

            userRepo.save(user);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

/*    public boolean identificationUser(String login, String password){
        return userRepo.searchByLoginAndPassword(login, password);
    }*/

    @Transactional
    public UserOfApp findUserBy(String login){ return userRepo.findByLogin(login);}

/*    @Transactional
    public UserOfApp findUserBy(String login, String password){ return userRepo.findByLoginAndPassword(login, password);}*/

    public List<UserOfApp> getUserList(){ return userRepo.findAll();}

    public void updateUserDetails(long userId, boolean status) {
        UserOfApp user = userRepo.findById(userId);

        user.setActive(status);

        userRepo.save(user);
    }

    public void updateUserDetails(String login, String newLogin, String newPassword) {
        UserOfApp user = userRepo.findByLogin(login);

        user.setLogin(newLogin);
        user.setPassword(newPassword);

        userRepo.save(user);
    }



    /**For impl UserDetailService*/
    @Transactional
    public Optional<UserOfApp> findUserByLoginOpt(String login){ return Optional.ofNullable(userRepo.findByLogin(login));}

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserOfApp user = findUserByLoginOpt(username).orElseThrow(() -> new UsernameNotFoundException("User not found") );
        return new User(user.getLogin(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }

    public void reloadUserByUsername(String login, Principal principal) throws UsernameNotFoundException {
        UserDetails userDetailsService = loadUserByUsername(login);
        try {
            ((Authentication) principal).setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetailsService, null, userDetailsService.getAuthorities()));

        } catch (IllegalArgumentException e) {
            log.error("Error changing user data: "+ e.getMessage());
            throw  e;
        }
    }
}
