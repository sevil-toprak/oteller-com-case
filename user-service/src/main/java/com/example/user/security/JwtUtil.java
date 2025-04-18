package com.example.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

//    private final SecretKey key;

//    public JwtUtil(JwtConfigProperties jwtConfigProperties) {
//        String secret = "thisisaverysecurekeythatworks123456";
//
//        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }

    @Value("${jwt.secret}")
    private String secretKey;


    public String generateToken(String userId, String role) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

//    public String generateToken(Long userId, String role) {
//        return Jwts.builder()
//                .setSubject(String.valueOf(userId))
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }

//    public Claims validateToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
}
