package com.example.springsecuritylogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() // https://iseunghan.tistory.com/302
            .authorizeRequests()
                // user 주소에 대해서 인증을 요구
                .antMatchers("/user/**").authenticated()
                // manager 주소는 ROLO_MANAGER 권한이나 ROLE_ADMIN 권한이 있어야 접근 가능
                .antMatchers("/manager/**").access("hasRole('MANAGER') or hasRole('ADMIN')")
                // admin 주소는 ROLE_ADMIN 권한이 있어야 접근 가능
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 나머지 주소는 인증 없이 접근 가능
                .anyRequest().permitAll()
            .and()
                .formLogin() // form 기반의 로그인인 경우
                    .loginPage("/loginForm")// 인증이 필요한 URL에 접근하면 /loginForm 으로 이동
                    .usernameParameter("username")// 로그인 시 form 에서 가져올 값(id, email 등이 해당)
                    .passwordParameter("password") // 로그인 시 form에서 가져올 값
                    .loginProcessingUrl("/login") // 로그인 처리할 URL 입력
                    .defaultSuccessUrl("/loginForm")// 로그인 성공하면 "/" 으로 이동
                    .failureUrl("/loginForm") // 로그인 실패 시 /loginForm 으로 이동
            .and()
            .logout() // logout 할 경우
            .logoutUrl("/logout") // 로그아웃을 처리할 URL 입력
            .logoutSuccessUrl("/loginForm")// 로그아웃 성공 시 "/"으로 이동
            .and().build();
    }
}
