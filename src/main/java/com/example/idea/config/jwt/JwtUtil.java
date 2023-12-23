package com.example.idea.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 900000)) // 토큰 유효 기간
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
