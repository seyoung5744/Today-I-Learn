package com.housing.back.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;


    public String create(String userId) {
        Date expriredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .signWith(key)
                .subject(userId)
                .issuedAt(new Date())
                .expiration(expriredDate)
                .compact();

        return jwt;
    }

    public String validate(String jwt) {

        String subject = null;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return subject;
    }
}
