package com.faezito.devToolsAPI.repository;

import com.faezito.devToolsAPI.model.UsuarioModel;
import com.faezito.devToolsAPI.repository.interfaces.IUsuarioRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UsuarioRepository implements IUsuarioRepository {
    private final NamedParameterJdbcTemplate db;

    public UsuarioRepository(NamedParameterJdbcTemplate db){
        this.db = db;
    }

    public List<UsuarioModel> Listar(Integer sistemaId, Integer usuarioId){
        String sql = """
SELECT
[ID] AS id
,[NomeCompleto] AS nomeCompleto
,[Usuario] AS usuario
,[Email] AS email
FROM Usuarios WHERE (:usuarioId IS NULL OR ID = :usuarioId)""";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("usuarioId", usuarioId);
        return  db.query(sql, params, new BeanPropertyRowMapper<>(UsuarioModel.class));
    }

    public UsuarioModel Obter(int id){
        String sql = """
SELECT
[ID]
,[NomeCompleto]
,[Usuario]
,[Email]
,[Senha]
FROM Usuarios WHERE ID = :usuarioId""";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("usuarioId", id);
        return db.query(sql, params, new BeanPropertyRowMapper<>(UsuarioModel.class))
                .stream().findFirst().orElse(null);
    }

    public int Inserir(UsuarioModel usuario){
        String sql = """
                INSERT INTO dbo.Usuarios
                ([NomeCompleto],[Usuario],[Email] ,[Senha])
                VALUES
                (:nomeCompleto, :usuario, :email, :senha)
                SELECT SCOPE_IDENTITY()
                """;
        SqlParameterSource param = new BeanPropertySqlParameterSource(usuario);
        return db.update(sql, param);
    }

    public void Atualizar(UsuarioModel usuario){
        String sql = """
                UPDATE dbo.Usuarios SET
                NomeCompleto = COALESCE(:nomeCompleto, NomeCompleto),
                Usuario = COALESCE(:usuario, Usuario),
                Email = COALESCE(:email, Email),
                Senha = COALESCE(:senha, Senha)
                WHERE ID = :id
                """;

        SqlParameterSource param = new BeanPropertySqlParameterSource(usuario);
        db.update(sql, param);
    }

    public void Deletar(int id){
        String sql = "DELETE FROM Usuarios WHERE ID = :id";
        Map<String, Object> params = Map.of("id", id);
        db.update(sql, params);
    }

    public UsuarioModel BuscarPorEmail(String email){
        String sql = "SELECT ID, Usuario, Email, Senha FROM dbo.Usuarios WHERE Email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
        return db.query(sql, params, new BeanPropertyRowMapper<>(UsuarioModel.class))
                .stream().findFirst().orElse(null);
    }

    public UsuarioModel BuscarPorUsuario(String usuario){
        String sql = "SELECT ID, Usuario, Email, Senha FROM dbo.Usuarios WHERE Usuario = :usuario";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("usuario", usuario);
        return db.query(sql, params, new BeanPropertyRowMapper<>(UsuarioModel.class))
                .stream().findFirst().orElse(null);
    }

}
