package com.faezito.devToolsAPI.model.DTOs;

import com.faezito.devToolsAPI.model.UsuarioModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    public String token;
    public UsuarioModel usuario;
}