package com.faezito.devToolsAPI.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.service.ChaveAPIService;
import com.faezito.devToolsAPI.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuario")
@Tag(name="Usuarios", description="Gestão de usuários")
public class UsuarioController {
    private final UsuarioService seUsuario;
    private final ChaveAPIService seChaveApi;

    public UsuarioController(UsuarioService seUsuario, ChaveAPIService seChaveApi){
        this.seUsuario = seUsuario;
        this.seChaveApi = seChaveApi;
    }

    @Operation(summary = "Listar", description = "Lista todos os usuários")
    @GetMapping("/Listar")
    public List<UsuarioModel> Listar(@RequestParam(required = false) Integer sistemaId,
                                     @RequestParam(required = false) Integer usuarioId)
    {
        try
        {
            return seUsuario.Listar(sistemaId, usuarioId);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Operation(summary = "Listar chaves do usuário", description = "Lista as Chaves de API do usuário")
    @GetMapping("/ListarChaves")
    public ResponseEntity<?> ListarChaves(HttpServletRequest req){
        UsuarioModel usuario = (UsuarioModel) req.getAttribute("usuarioAutenticado");
        System.out.println(req);
        return ResponseEntity.ok(seChaveApi.Listar(usuario.getId()));
    }

    @Operation(summary = "Obter usuario", description = "Obtém um usuário")
    @GetMapping("/Obter")
    public UsuarioModel Obter(@Parameter(description = "ID do usuário procurado") @RequestParam Integer id){
        try{
            UsuarioModel usuario = seUsuario.Obter(id);
            usuario.setSenha("");
            return usuario;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Operation(summary = "Obter usuario por chave de API", description = "Obtém um usuário")
    @PostMapping("/ObterPorChave")
    public String ObterPorChave(@Parameter(description = "API Key do usuário") @RequestBody String chaveAPI){
        try{
            UsuarioModel usuario = seChaveApi.BuscarUsuarioPorChave(chaveAPI);
            return usuario.nomeCompleto;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Operation(summary = "Cadastrar Usuário", description = "Cadastra um novo usuário")
    @SecurityRequirement(name = "")
    @PostMapping("/Inserir")
    public ResponseEntity<?> Inserir(@RequestBody UsuarioModel usuario){
        try
        {
            String rawKey = seUsuario.Inserir(usuario);
            return ResponseEntity.status(201).body(Map.of(
                    "apiKey", rawKey,
                    "aviso", "Guarde esta chave - ela não será exibida novamente"
            ));
        }
        catch (IllegalArgumentException ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Atualizar", description = "Atualizar/Edita um usuário")
    @PutMapping("/Atualizar/{id}")
    public void Atualizar(@RequestBody UsuarioModel usuario){
        try{
            seUsuario.Atualizar(usuario);
        }catch(Exception ex){
            throw ex;
        }
    }

    @Operation(summary = "Deletar usuário", description = "Deleta um usuário")
    @DeleteMapping("/Deletar/{id}")
    public void Deletar(@PathVariable Integer id){
        try
        {
            seUsuario.Deletar(id);
        }catch (Exception ex){
            throw ex;
        }
    }

}
