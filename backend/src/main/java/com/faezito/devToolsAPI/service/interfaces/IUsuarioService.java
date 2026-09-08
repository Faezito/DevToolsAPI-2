package com.faezito.devToolsAPI.service.interfaces;

import com.faezito.devToolsAPI.model.UsuarioModel;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioModel> Listar(Integer sistemaId, Integer usuarioId);
    UsuarioModel Login(String usuario, String senha);
    UsuarioModel Obter(int id);
    String Inserir(UsuarioModel usuario);
    void Atualizar(UsuarioModel usuario);
    void Deletar(int id);
}
