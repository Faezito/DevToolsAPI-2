package com.faezito.devToolsAPI.service.interfaces;

import com.faezito.devToolsAPI.model.ChamadoModel;

import java.util.List;

public interface IChamadoService {
    List<ChamadoModel> Listar(Integer sistemaId, Integer usuarioId, Integer atendenteId, Integer chamadoId);
    ChamadoModel Obter(int chamadoId);
    void Inserir(ChamadoModel model);
    void Editar(ChamadoModel model);
    void Excluir(Integer id);
    void Atribuir(ChamadoModel model);
}
