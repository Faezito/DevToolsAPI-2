package com.faezito.devToolsAPI.repository.interfaces;

import com.faezito.devToolsAPI.model.ChamadoModel;

import java.util.List;

public interface IChamadoRepository {
    List<ChamadoModel> Listar(Integer sistemaId, Integer usuarioId, Integer atendenteId, Integer chamadoId);
    void Inserir(ChamadoModel model);
    void Editar(ChamadoModel model);
    void Excluir(Integer id);
    void Atribuir(ChamadoModel model);
}
