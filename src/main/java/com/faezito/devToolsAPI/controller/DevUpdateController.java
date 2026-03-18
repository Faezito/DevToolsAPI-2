package com.faezito.devToolsAPI.controller;

import com.faezito.devToolsAPI.model.DevUpdateModel;
import com.faezito.devToolsAPI.service.DevUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/updates")
@Tag(name="Atualizações", description = "Notas de atualização dos sistemas")
public class DevUpdateController {
    private final DevUpdateService service;

    public DevUpdateController(DevUpdateService service) {
        this.service = service;
    }

    @GetMapping("/Listar/{sistemaId}")
    @Operation(summary = "Listar nota de atualização", description = "Lista as notas de atualização do sistema informado")
    public List<DevUpdateModel> Listar(@RequestParam Integer sistemaId)
    {
        return service.Listar(sistemaId);
    }

    @PostMapping("/Inserir")
    @Operation(summary = "Cadastrar nota de atualização", description = "Insere uma nova nota de atualização")
    public void Inserir(@RequestBody DevUpdateModel devUpdateModel)
    {
        //devUpdateModel.setDataAtualizacao(LocalDateTime.parse("2026-02-01"));
        devUpdateModel.setDataAtualizacao(LocalDateTime.now());
        service.Inserir(devUpdateModel);
    }

    @Operation(summary = "Editar ntoa de atualização", description = "Edita uma nota de atualização")
    @PutMapping("/Editar")
    public void Editar(@RequestBody DevUpdateModel devUpdateModel){
        service.Editar(devUpdateModel);
    }

    @Operation(summary = "Deletar nota de atualização")
    @DeleteMapping("/Deletar")
    public void Excluir(Integer id){
        service.Excluir(id);
    }
}
