package com.faezito.devToolsAPI.config;

import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.service.interfaces.IChaveAPIService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ChaveAPIFilter extends OncePerRequestFilter {
    private final IChaveAPIService keyService;
    private final String activeProfile;

    public ChaveAPIFilter(IChaveAPIService keyService, String activeProfile) {
        this.keyService = keyService;
        this.activeProfile = activeProfile;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req){
        if(activeProfile.equals("local")){
            return true;
        }

        String path = req.getRequestURI();
        System.out.println(">>> PATH: " + path);
        return path.contains("/usuario/Inserir") ||
                path.contains("/swagger-ui") ||
                path.contains("/usuario/Login") ||
                path.contains("/v3/api-docs") ||
                path.equals("/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
        throws ServletException, IOException{
        String chave = req.getHeader("X-API-Key");

        if(chave == null){
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"erro\": \"Header X-API-Key ausente\"}");
            return;
        }

        UsuarioModel usuario = keyService.BuscarUsuarioPorChave(chave);

        if (usuario == null) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"erro\": \"API Key inválida ou revogada\"}");
            return;
        }

        req.setAttribute("usuarioAutenticado", usuario);

        var auth = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }
}
