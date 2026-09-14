package com.faezito.devToolsAPI.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expiration;
    
    public JwtService(
        @Value("${JWT_SECRET}") String secret,
        @Value("${JWT_EXPIRATION}") long expiration) {
        
        this.key = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    public String gerarToken(Integer usuarioId, String login){
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                    .subject(login)
                    .claim("usuarioId", usuarioId)
                    .issuedAt(agora)
                    .expiration(expiracao)
                    .signWith(key)
                    .compact();
    }
}
