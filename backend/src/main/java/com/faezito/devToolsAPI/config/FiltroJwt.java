package com.faezito.devToolsAPI.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;

import java.io.IOException;
import java.util.List;

import com.faezito.devToolsAPI.model.UsuarioAutenticado;

public class FiltroJwt extends OncePerRequestFilter {

    private final SecretKey key;

    public FiltroJwt(SecretKey key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain) 
        throws ServletException, 
        IOException {
            String authorization = request.getHeader("Authorization");

            if(authorization == null || !authorization.startsWith("Bearer ")){
                    filterChain.doFilter(request, response);
                    return;
                }
            
            String token = authorization.substring(7);

            try {
                Claims claims = Jwts.parser()
                                .verifyWith(key)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

                String login = claims.getSubject();
                Integer usuarioId = claims.get("usuarioId", Integer.class);
                
                var usuarioAutenticado = new UsuarioAutenticado(
                    usuarioId, login
                );

                var auth = new UsernamePasswordAuthenticationToken(
                    usuarioAutenticado,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
                
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"erro\":\"Token JWT inválido ou expirado\"}"
                );
                return;
            }       
            
            filterChain.doFilter(request, response);
        }
    
}
