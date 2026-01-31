package com.example.reservation.util;

import com.example.reservation.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long expirationMs;

    public JwtUtil(AppProperties props){
        String secret = props.getJwt().getSecret();
        if (secret == null || secret.isBlank()) {
            this.key = Keys.hmacShaKeyFor(
                    "dev-fallback-secret-key-minimum-32-characters-required".getBytes()
            );
        } else {
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
        }
        this.expirationMs = props.getJwt().getExpirationMsec();
    }

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
