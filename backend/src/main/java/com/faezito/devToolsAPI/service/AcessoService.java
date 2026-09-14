package com.faezito.devToolsAPI.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faezito.devToolsAPI.config.JwtService;
import com.faezito.devToolsAPI.model.DTOs.LoginRequestDTO;
import com.faezito.devToolsAPI.model.DTOs.LoginResponseDTO;
import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.repository.interfaces.IUsuarioRepository;
import com.faezito.devToolsAPI.service.interfaces.IAcessoService;

@Service
public class AcessoService implements IAcessoService {
    private final IUsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AcessoService(IUsuarioRepository usuarioRepo,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDTO Login(LoginRequestDTO req) {
        UsuarioModel user = usuarioRepo.BuscarPorUsuario(req.getLogin());
        if(user == null)
            throw new RuntimeException("Credenciais inválidas");

        if(!passwordEncoder.matches(req.getSenha(), user.getSenha()))
            throw new RuntimeException("Credenciais inválidas");

        String token = jwtService.gerarToken(
            user.getId(),
            user.getUsuario()
        );

        user.setSenha("");

        return new LoginResponseDTO(
            token,
            user
        );
    }
}
