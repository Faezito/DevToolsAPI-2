package com.faezito.devToolsAPI.controller;

import com.faezito.devToolsAPI.model.SistemaModel;
import com.faezito.devToolsAPI.repository.interfaces.ISistemaRepository;
import com.faezito.devToolsAPI.service.SistemaService;
import com.faezito.devToolsAPI.service.interfaces.ISistemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sistema")
@Tag(name="Sistemas")
public class SistemaController {
    private final ISistemaService service;

    public SistemaController(ISistemaService service) {
        this.service = service;
    }

    @Operation(summary = "Obter Sistema")
    @GetMapping("/Obter/{id}")
    public SistemaModel Obter(@RequestParam int id){
        try{
            return service.Obter(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Listar sistemas")
    @GetMapping("/Listar")
    public List<SistemaModel> Listar(){
        try{
            return service.Listar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/Cadastro")
    @Operation(summary = "Cadastrar sistema")
    public String Cadastro(@RequestBody SistemaModel model){
        try{
            service.Inserir(model);
            return "Cadastrado com sucesso";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/Cadastro")
    @Operation(summary = "Cadastrar sistema")
    public String Atualizar(@RequestBody SistemaModel model){
        try{
            service.Atualizar(model);
            return "Atualizado com sucesso";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Deletar")
    @DeleteMapping("/Deletar/{id}")
    public boolean Deletar(@Parameter(description = "ID do sistema") @PathVariable Integer id){
        try{
            service.Deletar(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
