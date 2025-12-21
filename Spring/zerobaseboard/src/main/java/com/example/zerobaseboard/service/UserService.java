package com.example.zerobaseboard.service;

import com.example.zerobaseboard.model.LoginType;
import com.example.zerobaseboard.model.User;
import com.example.zerobaseboard.model.UserRole;
import com.example.zerobaseboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(User user) {
        String rawPassword = user.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodePassword);
        user.setLoginType(LoginType.GENERAL);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUser(String username) {
        return userRepository.findByUsername(username).orElseGet(User::new);
    }

    @Transactional
    public void updateUserInfo(User user) {
        Long id = user.getId();
        User currUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Failed to load User Info : cannot find User id"));

        if (currUser.getLoginType() == LoginType.GENERAL) {
            String rawPassword = user.getPassword();
            String encodePassword = passwordEncoder.encode(rawPassword);
            currUser.setPassword(encodePassword);
            currUser.setEmail(user.getEmail());
        }

    }


}
