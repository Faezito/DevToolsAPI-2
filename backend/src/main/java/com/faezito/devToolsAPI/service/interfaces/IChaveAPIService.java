package com.faezito.devToolsAPI.service.interfaces;

import com.faezito.devToolsAPI.model.ChaveAPIModel;
import com.faezito.devToolsAPI.model.UsuarioModel;

import java.util.List;

public interface IChaveAPIService {
    String CriarChave(Integer usuarioId, String nome);
    UsuarioModel BuscarUsuarioPorChave(String chave);
    List<ChaveAPIModel> Listar(Integer usuarioId);
    boolean Revogar(Integer chaveId, Integer usuarioId);
}
