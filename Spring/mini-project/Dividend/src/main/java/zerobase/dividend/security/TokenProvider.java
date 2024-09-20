package zerobase.dividend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zerobase.dividend.service.MemberService;

@Component
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
    private static final String KEY_ROLES = "roles";

    private final MemberService memberService;
    private final SecretKey secretKey;

    public TokenProvider(MemberService memberService, @Value("${spring.jwt.secret}") String secretKey) {
        this.memberService = memberService;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    // 토큰 생성(발급)
    public String generateToken(String username, List<String> roles, Date createDate) {

        var expireDate = new Date(createDate.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
            .subject(username)
            .claim(KEY_ROLES, roles)
            .issuedAt(createDate) // 토큰 생성 시간
            .expiration(expireDate) // 토큰 만료 시간
            .signWith(secretKey, Jwts.SIG.HS512) // 사용할 암호화 알고리즘, 비밀키
            .compact();
    }

    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = memberService.loadUserByUsername(getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateTokenWithNowDate(String token, Date now) {
        if (hasNotText(token)) {
            return false;
        }

        var expiration = this.parseClaims(token).getExpiration();
        // 만료 시간이 현재 시간보다 이전이면 이미 만료된 토큰이므로 유효X => expiration.before(now) => true => !true => false
        return !expiration.before(now); // expiration.after(now);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private boolean hasNotText(String token) {
        return !StringUtils.hasText(token);
    }
}
