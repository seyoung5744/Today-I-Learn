package zerobase.dividend.web;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.dividend.model.Auth;
import zerobase.dividend.security.TokenProvider;
import zerobase.dividend.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Auth.SingUp singUp) {
        var memberEntity = memberService.register(singUp);
        return ResponseEntity.ok(memberEntity);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody Auth.SignIn signIn) {
        var member = memberService.authenticate(signIn);
        Date createDate = new Date();
        var token = tokenProvider.generateToken(member.getUsername(), member.getRoles(), createDate);
        return ResponseEntity.ok(token);
    }
}
