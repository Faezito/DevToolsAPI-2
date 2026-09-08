package com.faezito.devToolsAPI.repository.interfaces;

import com.faezito.devToolsAPI.model.UsuarioModel;

import java.util.List;

public interface IUsuarioRepository {
    List<UsuarioModel> Listar(Integer sistemaId, Integer usuarioId);
    UsuarioModel Obter(int id);
    UsuarioModel BuscarPorEmail(String email);
    UsuarioModel BuscarPorUsuario(String usuario);
    int Inserir(UsuarioModel model);
    void Atualizar(UsuarioModel model);
    void Deletar(int usuarioId);
}
