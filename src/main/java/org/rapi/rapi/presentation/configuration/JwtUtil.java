package org.rapi.rapi.presentation.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.KeyPair;
import java.util.Date;
import org.rapi.rapi.application.auth.user.User;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final SignatureAlgorithm alg = SIG.RS512;
    private final KeyPair pair = alg.keyPair().build();

    public String generateToken(User user) {
        return Jwts.builder()
            .subject(user.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(pair.getPrivate(), alg)
            .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, User user) {
        return parseClaims(token).getExpiration().after(new Date())
            && parseClaims(token).getSubject().equals(user.getUsername());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(pair.getPublic())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
