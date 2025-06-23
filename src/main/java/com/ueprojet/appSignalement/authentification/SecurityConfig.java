package com.ueprojet.appSignalement.authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.ueprojet.appSignalement.authentification.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisez BCrypt pour encoder les mots de passe
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Utilisation de la nouvelle syntaxe
            
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Autoriser les requêtes OPTIONS
                .requestMatchers("api/auth/**").permitAll() // Pour Spring Security 5.4+
                .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("CITIZEN")
                .requestMatchers(HttpMethod.POST, "/api/problemes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/problemes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/problemes/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/arrondissements/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/arrondissements/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/arrondissements/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/signalements/**").hasRole("CITIZEN")
                .requestMatchers(HttpMethod.GET, "/api/signalements/getSignalements/users/**").hasRole("CITIZEN")
                .requestMatchers(HttpMethod.GET, "/api/signalements/**").hasRole("AGENT")
                .requestMatchers(HttpMethod.PUT, "/api/signalements/**").hasRole("AGENT")
                .requestMatchers(HttpMethod.GET, "/api/notifications/**").hasRole("CITIZEN")
                .requestMatchers(HttpMethod.DELETE, "/api/notifications/**").hasRole("CITIZEN")
                .requestMatchers(HttpMethod.POST, "/api/notifications/**").hasRole("AGENT")
                .requestMatchers(HttpMethod.GET, "/api/citoyen/stats/**").hasRole("CITIZEN")
                .requestMatchers(HttpMethod.GET, "/api/stats/agent/**").hasRole("AGENT")
                .requestMatchers(HttpMethod.GET, "/api/stats/admin/**").hasRole("ADMIN")
                .requestMatchers("/h2-console/**").permitAll() // Permettre l'accès à la console H2) 
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .headers(headers -> headers
                .frameOptions().sameOrigin()); // Permettre l'affichage de la console H2

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build(); // Nécessaire pour retourner le SecurityFilterChain
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}