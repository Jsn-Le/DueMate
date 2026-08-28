package com.duemate.duemate.security;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String createJWT(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String tokenEmail = extractEmail(token);
        String userEmail = userDetails.getUsername();
        if (!tokenEmail.equals(userEmail)) {
            return false;
        }

        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().getExpiration();
        if (expiration.before(Date.from(Instant.now()))) {
            return false;
        }

        return true;
    }

}
