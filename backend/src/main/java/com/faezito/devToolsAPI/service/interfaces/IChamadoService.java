package com.faezito.devToolsAPI.service.interfaces;

import com.faezito.devToolsAPI.model.ChamadoModel;
import com.faezito.devToolsAPI.model.DTOs.ChamadoFechamentoDTO;
import com.faezito.devToolsAPI.model.DTOs.ChamadoRequestDTO;

import java.util.List;

public interface IChamadoService {
    List<ChamadoModel> Listar(ChamadoRequestDTO req);
    ChamadoModel Obter(int chamadoId);
    void Inserir(ChamadoModel model);
    void Editar(ChamadoModel model);
    void Excluir(Integer id);
    void Atribuir(ChamadoModel model);
    void FecharChamado(ChamadoFechamentoDTO dto);
}
