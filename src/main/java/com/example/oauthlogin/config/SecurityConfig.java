package com.example.oauthlogin.config;

import com.example.oauthlogin.repositories.UserRepository;
import com.example.oauthlogin.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER - 3)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
                .requestMatchers(config ->
                        config.antMatchers(
                                "/login",
                                "/logout"))
                .authorizeRequests(authorise -> authorise
                        .antMatchers("/login",
                                "/logout")
                        .permitAll()
                        .antMatchers("/main", "/", "/collection").authenticated())
                .formLogin(c -> c.loginPage("/login")
                )
                .authenticationManager(passwordAuthenticationManager()).userDetailsService(userDetailsService());
        http.logout().logoutSuccessUrl("/login?logout");
        return http.build();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorise -> authorise
                        .antMatchers("/login-oauth",
                                "/public/images/**",
                                "/images/**",
                                "/static/**",
                                "/home",
                                "/register",
                                "/logout",
                                "/movie/**",
                                "/catalog",
                                "/oauth2/authorization/github/**",
                                "/login/oauth2/code/github/**").permitAll().anyRequest()
//                        .antMatchers("/main", "/", "/collection")

                        .authenticated());
        http.oauth2Login().loginPage("/login-oauth").clientRegistrationRepository(clientRegistrationRepository);
        http.logout().logoutSuccessUrl("/login-oauth?logout");
        return http.build();
    }

    @Bean
    public AuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager passwordAuthenticationManager() {
        return new ProviderManager(daoAuthProvider());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST"));
        return firewall;
    }


}
