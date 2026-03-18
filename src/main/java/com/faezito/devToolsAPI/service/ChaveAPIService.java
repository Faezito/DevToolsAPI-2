package com.faezito.devToolsAPI.service;

import com.faezito.devToolsAPI.model.ChaveAPIModel;
import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.repository.UsuarioRepository;
import com.faezito.devToolsAPI.repository.interfaces.IChaveAPIRepository;
import com.faezito.devToolsAPI.repository.interfaces.IUsuarioRepository;
import com.faezito.devToolsAPI.service.interfaces.IChaveAPIService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ChaveAPIService implements IChaveAPIService {
    private final IChaveAPIRepository repo;
    private final IUsuarioRepository usuario;

    public ChaveAPIService(IChaveAPIRepository repo, IUsuarioRepository usuario) {
        this.repo = repo;
        this.usuario = usuario;
    }


    @Override
    public String CriarChave(Integer usuarioId, String nome)
    {
        String chave = "dt_" + UUID.randomUUID().toString().replace("-", "");

        ChaveAPIModel key = new ChaveAPIModel();
        key.setDataCriacao(LocalDateTime.now());
        key.setUsuarioId(usuarioId);
        key.setHashChave(Hash(chave));
        key.setPrefixo(chave.substring(0,13) + "...");
        key.setNome(nome);
        repo.Inserir(key);

        return chave;
    }

    @Override
    public UsuarioModel BuscarUsuarioPorChave(String chave) {
        String hash = Hash(chave);
        System.out.println(">>> CHAVE ENCONTRADA");
        ChaveAPIModel key = repo.Obter(hash);
        if(key == null) return null;
        return usuario.Obter(key.usuarioId);
    }

    @Override
    public List<ChaveAPIModel> Listar(Integer usuarioId) {
        return repo.ListarChavesDoUsuario(usuarioId);
    }

    @Override
    public boolean Revogar(Integer chaveId, Integer usuarioId) {
        if (repo.ContarAtivasPorUsuario(usuarioId) <= 1){
            throw new IllegalStateException("Não é possível revogar a única chave ativa");
        }
        return repo.RevogarChave(chaveId, usuarioId);
    }


    private String Hash(String chave)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(chave.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
