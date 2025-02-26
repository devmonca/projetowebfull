package com.example.criandoapi.projeto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/usuarios/login")
                        .ignoringRequestMatchers("/usuarios")
                        .ignoringRequestMatchers("/usuarios/{id}")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/usuarios").permitAll()
                        .anyRequest().authenticated()
                )
                .cors();

        http.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
