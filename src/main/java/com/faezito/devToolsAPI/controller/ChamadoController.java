package com.faezito.devToolsAPI.controller;

import com.faezito.devToolsAPI.model.ChamadoModel;
import com.faezito.devToolsAPI.model.DTOs.ChamadoFechamentoDTO;
import com.faezito.devToolsAPI.model.DTOs.ChamadoRequestDTO;
import com.faezito.devToolsAPI.service.interfaces.IChamadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chamados")
@Tag(name="Chamados", description = "Chamados de Suporte")
public class ChamadoController {
    private final IChamadoService service;

    public ChamadoController(IChamadoService service) {
        this.service = service;
    }

    @GetMapping("/Obter/{id}")
    // Operation define a descrição da rota no swagger
    @Operation(summary = "Listar Chamados", description = "Lista os chamados do sistema informado")
    public ChamadoModel Obter(@Parameter(description = "ID do Chamado", example = "1") @PathVariable Integer id)
    {
        return service.Obter(id);
    }

    @GetMapping("/Listar")
    @Operation(summary = "Listar Chamados", description = "Lista os chamados do sistema informado")
    public List<ChamadoModel> Listar(@ParameterObject ChamadoRequestDTO req)
    {
        System.out.println(">>> REQ: " + req);
        return service.Listar(req);
    }

    @PostMapping("/Cadastrar")
    @Operation(summary = "Cadastrar Chamado", description = "Insere um novo chamado")
    public String cadastrar(@RequestBody ChamadoModel chamado)
    {
        chamado.setDataAbertura(LocalDateTime.now());
        service.Inserir(chamado);

        return "Chamado criado com sucesso";
    }

    @Operation(summary = "Editar Chamado", description = "Edita um chamado")
    @PutMapping("/Editar")
    public String Atualizar(@RequestBody ChamadoModel chamado)
    {
        chamado.setDataAlteracao(LocalDateTime.now());
        service.Editar(chamado);

        return "Chamado alterado com sucesso";
    }

    @Operation(summary = "Atribuir Chamado", description = "Atribui um atendente para o chamado")
    @PutMapping("/Atribuir")
    public String atribuir(@RequestBody ChamadoModel chamado)
    {
        chamado.setDataAlteracao(LocalDateTime.now());
        service.Atribuir(chamado);

        return "Atribuição concluída";
    }


    @Operation(summary = "Fechar Chamado", description = "Fecha um chamado")
    @PutMapping("/Fechar")
    public String Fechar(@RequestBody ChamadoFechamentoDTO dto)
    {
        service.FecharChamado(dto);

        return "Chamado fechado com sucesso";
    }

    @Operation(summary = "Deletar Chamado")
    @DeleteMapping("/Deletar/{id}")
    public String deletar(@Parameter(description = "ID do Chamado", example = "2") @PathVariable Integer id){
        service.Excluir(id);

        return "Deletado com sucesso!";
    }
}
