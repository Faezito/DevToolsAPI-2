package com.faezito.devToolsAPI.service;

import com.faezito.devToolsAPI.model.SistemaModel;
import com.faezito.devToolsAPI.repository.interfaces.ISistemaRepository;
import com.faezito.devToolsAPI.service.interfaces.ISistemaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SistemaService implements ISistemaService {
    private final ISistemaRepository repo;

    public SistemaService(ISistemaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Integer Inserir(SistemaModel model) {
        return repo.Inserir(model);
    }

    @Override
    public void Atualizar(SistemaModel model) {
        repo.Atualizar(model);
    }

    @Override
    public List<SistemaModel> Listar() {
        return repo.Listar();
    }

    @Override
    public SistemaModel Obter(Integer sistemaId) {
        return repo.Obter(sistemaId);
    }

    @Override
    public void Deletar(Integer sistemaId) {
        repo.Deletar(sistemaId);
    }
}
