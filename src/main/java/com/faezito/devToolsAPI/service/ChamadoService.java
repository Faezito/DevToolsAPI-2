package com.faezito.devToolsAPI.service;

import com.faezito.devToolsAPI.model.ChamadoModel;
import com.faezito.devToolsAPI.model.DTOs.ChamadoFechamentoDTO;
import com.faezito.devToolsAPI.model.DTOs.ChamadoRequestDTO;
import com.faezito.devToolsAPI.repository.interfaces.IChamadoRepository;
import com.faezito.devToolsAPI.service.interfaces.IChamadoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadoService implements IChamadoService {
    private final IChamadoRepository repository;

    public ChamadoService(IChamadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChamadoModel> Listar(ChamadoRequestDTO req) {
        return repository.Listar(req);
    }

    @Override
    public ChamadoModel Obter(int chamadoId) {
        var dto = new ChamadoRequestDTO();
        dto.chamadoId = chamadoId;
        var lst = repository.Listar(dto);
        return lst.stream().findFirst().orElse(null);
    }

    @Override
    public void Inserir(ChamadoModel model) {
        repository.Inserir(model);
    }

    @Override
    public void Editar(ChamadoModel model) {
        repository.Editar(model);
    }

    @Override
    public void Excluir(Integer id) {
        repository.Excluir(id);
    }

    @Override
    public void Atribuir(ChamadoModel model) {
        repository.Atribuir(model);
    }

    @Override
    public void FecharChamado(ChamadoFechamentoDTO dto) {
        repository.FecharChamado(dto);
    }
}
