package com.example.mushroomsdetect.config;

import com.example.mushroomsdetect.services.impl.CustomUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig   {

    private final CustomUserDetailServiceImpl userDetailService;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setPasswordEncoder(passwordEncoder());
       provider.setUserDetailsService(userDetailService);

       return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .httpBasic(Customizer.withDefaults())
                 .csrf((csrf) -> {
                     csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                 })
                 .formLogin((login) ->{
                     login.loginPage("/login");
                     login.defaultSuccessUrl("/welcome");
                     login.permitAll();
                 })
                 .logout((logout) ->{
                     logout.logoutUrl("/logout");
                     logout.logoutSuccessUrl("/welcome");
                     logout.permitAll();
                 })
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/welcome/**", "/").permitAll();
                    auth.requestMatchers("/registration", "/processRegistration").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                });
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

