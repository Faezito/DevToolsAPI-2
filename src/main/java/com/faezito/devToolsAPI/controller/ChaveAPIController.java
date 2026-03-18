package com.faezito.devToolsAPI.controller;

import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.service.interfaces.IChaveAPIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Chave de API")
public class ChaveAPIController {
    private final IChaveAPIService seChaveApi;

    public ChaveAPIController(IChaveAPIService seChaveApi) {
        this.seChaveApi = seChaveApi;
    }

    @Operation(summary = "Listar chaves do usuário", description = "Lista as Chaves de API do usuário")
    @GetMapping("/ListarChaves")
    public ResponseEntity<?> ListarChaves(HttpServletRequest req){
        UsuarioModel usuario = (UsuarioModel) req.getAttribute("usuarioAutenticado");
        System.out.println(req);
        return ResponseEntity.ok(seChaveApi.Listar(usuario.getId()));
    }

    @Operation(summary = "Criar chave de API")
    @PostMapping("/CriarChave")
    public ResponseEntity<?> CriarChave(@RequestBody Map<String, String> body,
                                        HttpServletRequest req){
        UsuarioModel usuario = (UsuarioModel) req.getAttribute("usuarioAutenticado");
        String chave = seChaveApi.CriarChave(usuario.getId(), body.get("nome"));
        return ResponseEntity.status(201).body(Map.of(
                "apiKey", chave,
                "aviso", "Guarde esta chave - ela não será exibida novamente"
        ));
    }
    @Operation(summary = "Revogar chave de API")
    @DeleteMapping("/RevogarKey/{id}")
    public ResponseEntity<?> RevogarChave(@PathVariable Integer id, HttpServletRequest req)
    {
        UsuarioModel usuario = (UsuarioModel) req.getAttribute("usuarioAutenticado");
        try{
            boolean ok = seChaveApi.Revogar(id, usuario.getId());
            return ok ? ResponseEntity.ok("Chave revogada")
                    : ResponseEntity.status(404).body("Chave não encontrada");
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
