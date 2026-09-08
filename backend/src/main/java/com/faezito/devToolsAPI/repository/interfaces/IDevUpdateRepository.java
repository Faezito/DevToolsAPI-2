package com.faezito.devToolsAPI.repository.interfaces;

import com.faezito.devToolsAPI.model.DevUpdateModel;

import java.util.List;

public interface IDevUpdateRepository {
    List<DevUpdateModel> Listar(Integer sistemaId);
    DevUpdateModel Obter(Integer id);
    void Inserir(DevUpdateModel devUpdateModel);
    void Editar(DevUpdateModel devUpdateModel);
    void Excluir(Integer id);
    void Limpar(Integer sistemaId);
};


