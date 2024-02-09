package com.example.mushroomsdetect.services.impl;

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

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

    final UserRepo userRepo;

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

    @Transactional
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
