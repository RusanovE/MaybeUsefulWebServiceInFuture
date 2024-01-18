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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final CustomUserDetailServiceImpl userDetailService;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setPasswordEncoder(passwordEncoder());
       provider.setUserDetailsService(userDetailService);

       return provider;
    }


    @Bean  //TODO Сделать перевод на собственную обработку входа выхода
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .httpBasic(Customizer.withDefaults())

                 .formLogin(Customizer.withDefaults())
                 .logout(Customizer.withDefaults())
//                 .formLogin((login) ->{
//                     login.loginPage("/login");
//                     login.permitAll();
//                 })
//                 .logout((logout) ->{
//                     logout.logoutUrl("/logout");
//                     logout.permitAll();
//                 })
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/welcome").permitAll();
                    auth.requestMatchers("/registration", "/processRegistration").authenticated();
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

