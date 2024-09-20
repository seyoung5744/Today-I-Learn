package zerobase.dividend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.dividend.model.Auth;
import zerobase.dividend.persist.MemberRepository;
import zerobase.dividend.persist.domain.MemberEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }

    public MemberEntity register(Auth.SingUp singUp) {
        if (memberRepository.existsByUsername(singUp.getUsername())) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다");
        }

        singUp.encodePassword(passwordEncoder.encode(singUp.getPassword()));

        return memberRepository.save(singUp.toEntity());
    }

    public MemberEntity authenticate(Auth.SignIn signIn) {
        return null;
    }
}
