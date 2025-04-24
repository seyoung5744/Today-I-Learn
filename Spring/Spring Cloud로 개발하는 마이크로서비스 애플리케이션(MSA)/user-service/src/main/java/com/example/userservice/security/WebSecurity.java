package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());
        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/welcome")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/health-check")).permitAll()
//                .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
