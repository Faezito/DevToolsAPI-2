package com.faezito.devToolsAPI.repository.interfaces;

import com.faezito.devToolsAPI.model.ChaveAPIModel;

import java.util.List;

public interface IChaveAPIRepository {
    void Inserir(ChaveAPIModel chave);
    ChaveAPIModel Obter(String HashChave);
    List<ChaveAPIModel> ListarChavesDoUsuario(Integer usuarioId);
    int ContarAtivasPorUsuario(Integer usuarioId);
    boolean RevogarChave(Integer chaveId, Integer usuarioId);
}
