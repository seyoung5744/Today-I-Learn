package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    // HttpSecurity for 권한
    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http.csrf((csrf) -> csrf.disable());
        // 응답 헤더에 X-Frame-Options 추가 - 클릭재킹 공격 방어 - 동일 출처만 <iframe> 로드 가능.
        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
                        /*requestMatchers(new AntPathRequestMatcher("/**")).permitAll()*/
                                .requestMatchers("/**").access((authentication, request) -> {
                                    String clientIp = request.getRequest().getRemoteAddr();
                                    log.debug("client ip is = {}", clientIp);

                                    // 허용된 IP 리스트
                                    String[] allowedIps = {"192.168.0.141", "172.30.1.71"};

                                    // IP가 허용된 리스트에 포함되어 있는지 확인
                                    boolean isAllowed = Arrays.asList(allowedIps).contains(clientIp);

                                    return new AuthorizationDecision(isAllowed);
                                })
                )
                .authenticationManager(authenticationManager);

        http.addFilter(getAuthenticationFilter(authenticationManager));

        return http.build();
    }


    // for 인증
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        return authenticationManagerBuilder.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }

    // UserServiceImpl 이랑 순환 참조 발생 -> 일단 실습을 위해 UserServiceApplication 로 이동
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
