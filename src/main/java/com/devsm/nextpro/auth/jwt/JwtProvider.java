package com.devsm.nextpro.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final Long expirationTime;

    public JwtProvider(@Value("${spring.jwt.secret}")String secret,
    @Value("${spring.jwt.expiration}")Long expirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationTime = expirationTime;
    }

    // 토큰 생성
    public String create(String username, String uuid ,String role) {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);
        String BEARER = "Bearer ";
        return BEARER +
                Jwts.builder()
                        .claim("username", username)
                        .claim("uuid", uuid)
                        .claim("role", role)
                        .issuedAt(now)
                        .expiration(expiration)
                        .signWith(secretKey,Jwts.SIG.HS256)
                        .compact();
    }
}
