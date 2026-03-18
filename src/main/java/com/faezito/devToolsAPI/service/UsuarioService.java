package com.faezito.devToolsAPI.service;

import com.faezito.devToolsAPI.model.ChaveAPIModel;
import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.repository.UsuarioRepository;
import com.faezito.devToolsAPI.repository.interfaces.IUsuarioRepository;
import com.faezito.devToolsAPI.service.interfaces.IChaveAPIService;
import com.faezito.devToolsAPI.service.interfaces.IUsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    private final IUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final IChaveAPIService chaveAPIService;

    public UsuarioService(IUsuarioRepository repository, PasswordEncoder passwordEncoder, IChaveAPIService chaveAPIService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.chaveAPIService = chaveAPIService;
    }

    @Override
    public List<UsuarioModel> Listar(Integer sistemaId, Integer usuarioId){
        return repository.Listar(sistemaId, usuarioId);
    }

    @Override
    public UsuarioModel Login(String usuario, String senha) {
        UsuarioModel user = repository.BuscarPorUsuario(usuario);
        if(user == null)
            throw new RuntimeException("Usuário não encontrado!");

        if(!passwordEncoder.matches(senha, user.getSenha()))
            throw new RuntimeException("Credenciais inválidas");

        List<ChaveAPIModel> chaves = chaveAPIService.Listar(user.id);
        user.setChaves(chaves);
        user.setSenha("");
        return user;

        /* OUTRAS FORMAS
        user.getChaves().addAll(chaves);

        for(ChaveAPIModel item : chaves){
            user.getChaves().add(item);
        }

         */
    }

    @Override
    public UsuarioModel Obter(int id){
        return repository.Obter(id);
    }

    @Override
    public String Inserir(UsuarioModel usuario){
        if(repository.BuscarPorEmail(usuario.getEmail()) != null)
            throw new IllegalArgumentException("Este e-mail já está em uso");

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        int id = repository.Inserir(usuario);

        UsuarioModel novoUsuario = repository.BuscarPorEmail(usuario.getEmail());
        return chaveAPIService.CriarChave(novoUsuario.getId(), "Default");
    }

    @Override
    public void Atualizar(UsuarioModel usuario){
        repository.Atualizar(usuario);
    }

    @Override
    public void Deletar(int id){
        repository.Deletar(id);
    }
}
