package com.example.zerobaseboard.controller;

import com.example.zerobaseboard.config.auth.PrincipalDetailService;
import com.example.zerobaseboard.dto.ResponseDto;
import com.example.zerobaseboard.model.User;
import com.example.zerobaseboard.service.UserService;
import com.fasterxml.jackson.core.TreeCodec;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    private final PrincipalDetailService principalDetailService;
    private final TreeCodec treeCodec;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Boolean> save(@RequestBody User user) {
        userService.signUp(user);
        return new ResponseDto<>(HttpStatus.CREATED, true);
    }

    @PutMapping("/user")
    public ResponseDto<Boolean> update(@RequestBody User user, HttpSession session) {
        userService.updateUserInfo(user);
        UserDetails userDetails = principalDetailService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return new ResponseDto<>(HttpStatus.OK, false);
    }

    @GetMapping("/auth/username/{userName}")
    public ResponseDto<Boolean> checkUserName(@PathVariable String userName) {
        User selectedUser = userService.findUser(userName);
        if (selectedUser.getUsername() != null) {
            return new ResponseDto<>(HttpStatus.OK, false);
        }
        return new ResponseDto<>(HttpStatus.OK, true);
    }
}
