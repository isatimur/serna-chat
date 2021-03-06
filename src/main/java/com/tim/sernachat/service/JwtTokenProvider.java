package com.tim.sernachat.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;


@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenValid = 1000L * 60 * 60;

    @SuppressWarnings("deprecated")
    public String generateToken(String name) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Date now = Date.from(Instant.now());
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValid))
                .signWith(key)
                .compact();
    }

    public String getUserFromToken(String token) {
        return getClaims(token).getBody().getId();
    }

    public boolean validateToken(String token) {
        return this.getClaims(token) != null;
    }

    @SuppressWarnings("deprecated")
    private Jws<Claims> getClaims(String token) {

        try {
            return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))).build().parseClaimsJws(token);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
            throw ex;
        }
    }

}
