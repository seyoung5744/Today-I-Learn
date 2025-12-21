package com.example.zerobaseboard.config.auth;

import com.example.zerobaseboard.model.User;
import com.example.zerobaseboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    //해당 username이 DB에 있는지 확인
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찹을 수 없습니다." + username));
        return new PrincipalDetail(user); // 시큐리티 세션에 저장
    }
}
