package com.faezito.devToolsAPI.service;

import com.faezito.devToolsAPI.model.DevUpdateModel;
import com.faezito.devToolsAPI.repository.interfaces.IDevUpdateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevUpdateService {
    private final IDevUpdateRepository repository;

    public DevUpdateService(IDevUpdateRepository repository) {
        this.repository = repository;
    }

    public List<DevUpdateModel> Listar(Integer sistemaId){
        List<DevUpdateModel> lst = repository.Listar(sistemaId);
        repository.Limpar(sistemaId);
        return lst;
    }

    public DevUpdateModel Obter(Integer id){
        return repository.Obter(id);
    }

    public void Inserir(DevUpdateModel devUpdateModel){ repository.Inserir(devUpdateModel); }

    public void Excluir(Integer id){ repository.Excluir(id); }

    public void Editar(DevUpdateModel devUpdateModel){ repository.Editar(devUpdateModel); }
}
