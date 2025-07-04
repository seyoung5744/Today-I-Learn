package com.housing.back.filter;

import com.housing.back.entity.UserEntity;
import com.housing.back.provider.JwtProvider;
import com.housing.back.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return List.of(
                "/",
                "/api/v1/auth/id-check",
                "/api/v1/auth/email-certification",
                "/api/v1/auth/check-certification",
                "/api/v1/auth/sign-up",
                "/api/v1/auth/sign-in"
        ).contains(requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("요청 URI : {}", request.getRequestURI());
        try {
            String token = parseBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtProvider.validate(token);
            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            String role = userEntity.getRole();// role: ROLE_USER, ROLE_ADMIN
            log.info("권한 {} ", role);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);

        if (!hasAuthorization) {
            return null;
        }

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) {
            return null;
        }

        String token = authorization.replace("Bearer ", "");
        return token;
    }
}
