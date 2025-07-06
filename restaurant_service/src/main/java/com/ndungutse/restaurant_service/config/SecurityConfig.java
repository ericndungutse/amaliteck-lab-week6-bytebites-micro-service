package com.ndungutse.restaurant_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection as this is a microservice (stateless API)
                .csrf(AbstractHttpConfigurer::disable)

                // Disable form login
                .formLogin(AbstractHttpConfigurer::disable)

                // Disable HTTP Basic authentication
                .httpBasic(AbstractHttpConfigurer::disable)

                // Set session creation policy to stateless (no sessions)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure authorization - permit all requests
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())

                // Disable default logout
                .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
