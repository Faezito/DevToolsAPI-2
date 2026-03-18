package com.faezito.devToolsAPI.repository.interfaces;

import com.faezito.devToolsAPI.model.SistemaModel;

import java.util.List;

public interface ISistemaRepository {
    Integer Inserir(SistemaModel model);
    void Atualizar(SistemaModel model);
    List<SistemaModel> Listar();
    SistemaModel Obter(Integer sistemaId);
    void Deletar(Integer sistemaId);
}
