package com.example.springsecuritylogin.auth;

import com.example.springsecuritylogin.domain.User;
import com.example.springsecuritylogin.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        User byUsername = userRepository.findByUsername(username);
        log.info(String.valueOf(byUsername));

        if (Objects.nonNull(byUsername)) {
            return new PrincipalDetails(byUsername);
        }

        return null;
    }
}
