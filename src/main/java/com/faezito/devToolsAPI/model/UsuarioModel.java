package com.faezito.devToolsAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
    public Integer id;
    public String nomeCompleto;
    public String usuario;
    public String email;
    public String senha;
    public List<ChaveAPIModel> chaves;
}