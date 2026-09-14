package com.faezito.devToolsAPI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faezito.devToolsAPI.model.DTOs.LoginResponseDTO;
import com.faezito.devToolsAPI.model.DTOs.LoginRequestDTO;
import com.faezito.devToolsAPI.service.interfaces.IAcessoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/acesso")
@Tag(name="Acesso")
public class AcessoController {
    private final IAcessoService acessoService;

    public AcessoController(IAcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @Operation(summary = "Login")
    @PostMapping("/Login")
    public LoginResponseDTO Login(@RequestBody LoginRequestDTO login){
        try{
            return acessoService.Login(login);
        }catch (Exception e){
            throw e;
        }
    }
}