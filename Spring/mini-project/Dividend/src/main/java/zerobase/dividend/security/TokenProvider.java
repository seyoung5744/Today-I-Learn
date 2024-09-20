package zerobase.dividend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
    private static final String KEY_ROLES = "roles";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    // 토큰 생성(발급)
    public String generateToken(String username, List<String> roles, Date createDate) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var expireDate = new Date(createDate.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(createDate) // 토큰 생성 시간
            .setExpiration(expireDate) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS512, this.secretKey) // 사용할 암호화 알고리즘, 비밀키
            .compact();
    }

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateTokenWithNowDate(String token, Date now) {
        if(hasNotText(token)) {
            return false;
        }

        var expiration = this.parseClaims(token).getExpiration();
        // 만료 시간이 현재 시간보다 이전이면 이미 만료된 토큰이므로 유효X => expiration.before(now) => true => !true => false
        return !expiration.before(now); // expiration.after(now);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private boolean hasNotText(String token) {
        return !StringUtils.hasText(token);
    }
}
