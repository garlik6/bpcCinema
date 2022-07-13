package com.example.oauthlogin.config;

import com.example.oauthlogin.repositories.UserRepository;
import com.example.oauthlogin.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorise -> authorise.antMatchers("/public/images/**", "/images/**", "/static/**", "/login", "/home", "/register","/logout").permitAll()
                        .anyRequest().authenticated());
        http.oauth2Login().loginPage("/login")
                .clientRegistrationRepository(clientRegistrationRepository);
        http.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .clearAuthentication(true).logoutSuccessUrl("/login?logout");
        http.formLogin(c -> c.loginPage("/login"));
        http.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .clearAuthentication(true).logoutSuccessUrl("/login?logout");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
