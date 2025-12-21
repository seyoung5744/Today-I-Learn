package com.example.zerobaseboard.config.auth;

import com.example.zerobaseboard.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//스프링 시큐리티가 로그인 요청을 가로채서 완료 시 userDetails 타입의 Object를 고유한 세션 저장소에 저장
@Getter
public class PrincipalDetail implements UserDetails {

    private final User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_" + user.getRole(); //ROLE_USER ROLE_는 무조건 적어줘야함 규칙
        });
        return collectors;
    }
}
