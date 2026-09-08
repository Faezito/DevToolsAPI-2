package com.faezito.devToolsAPI.repository;

import com.faezito.devToolsAPI.model.ChaveAPIModel;
import com.faezito.devToolsAPI.repository.interfaces.IChaveAPIRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChaveAPIRepository implements IChaveAPIRepository {
    private final NamedParameterJdbcTemplate db;

    public ChaveAPIRepository(NamedParameterJdbcTemplate db) {
        this.db = db;
    }

    @Override
    public void Inserir(ChaveAPIModel chave)
    {
            String sql = """
                    INSERT INTO dbo.ChavesAPI
                    ([UsuarioID]
                     ,[HashChave]
                     ,[Prefixo]
                     ,[Nome]
                     ,[Ativa]
                     ,[DataCriacao])
                     VALUES
                     (
                     :usuarioId,
                     :hashChave,
                     :prefixo,
                     :nome,
                     1,
                     :dataCriacao
                     )
                    """;
            SqlParameterSource params = new BeanPropertySqlParameterSource(chave);
            db.update(sql, params);
    }

    @Override
    public ChaveAPIModel Obter(String HashChave)
    {
            String sql = """
                    SELECT
                        ID AS id,
                        UsuarioID AS usuarioId,
                        HashChave AS hashChave,
                        Prefixo AS prefixo,
                        Nome AS nome,
                        Ativa AS ativa,
                        DataCriacao AS dataCriacao
                    FROM dbo.ChavesAPI
                    WHERE HashChave = :hashChave AND Ativa = 1
                    """;
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("hashChave", HashChave);
        return db.query(sql, params, new BeanPropertyRowMapper<>(ChaveAPIModel.class))
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<ChaveAPIModel> ListarChavesDoUsuario(Integer usuarioId)
    {
            String sql = """
        SELECT * FROM dbo.ChavesAPI
        WHERE UsuarioID = :usuarioId""";

         MapSqlParameterSource params = new MapSqlParameterSource()
                 .addValue("usuarioId", usuarioId);
         return db.query(sql, params, new BeanPropertyRowMapper<>(ChaveAPIModel.class));
    }

    @Override
    public int ContarAtivasPorUsuario(Integer usuarioId)
    {
        String sql = "SELECT COUNT(*) FROM dbo.ChavesAPI WHERE UsuarioID = :usuarioId AND Ativa = 1";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("usuarioId", usuarioId);
        return db.queryForObject(sql, params, Integer.class);
    }

    @Override
    public boolean RevogarChave(Integer chaveId, Integer usuarioId)
    {
        String sql = "UPDATE ApiKeys SET Ativa = 0 WHERE ID = :chaveId AND UsuarioID = :usuarioId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("chaveId", chaveId)
                .addValue("usuarioId", usuarioId);

        return db.update(sql, params) > 0;
    }
}
