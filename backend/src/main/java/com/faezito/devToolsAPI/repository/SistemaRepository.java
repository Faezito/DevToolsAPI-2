package com.faezito.devToolsAPI.repository;


import com.faezito.devToolsAPI.model.SistemaModel;
import com.faezito.devToolsAPI.repository.interfaces.ISistemaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SistemaRepository implements ISistemaRepository {
    private final NamedParameterJdbcTemplate db;

    public SistemaRepository(NamedParameterJdbcTemplate db) {
        this.db = db;
    }

    @Override
    public Integer Inserir(SistemaModel model) {
        String sql = """
                INSERT INTO Sistemas ([Descricao],[Url])
                VALUES (:descricao, :url)
                """;
        SqlParameterSource params = new BeanPropertySqlParameterSource(model);
        return db.update(sql, params);
    }

    @Override
    public void Atualizar(SistemaModel model) {
        String sql = """
                UPDATE Sistemas SET
                [Descricao] = COALESCE(:descricao, Descricao),
                [Url] = COALESCE(:url, Url)
                WHERE ID = :id
                """;
        SqlParameterSource params = new BeanPropertySqlParameterSource(model);
        db.update(sql, params);
    }

    @Override
    public List<SistemaModel> Listar() {
        String sql = """
SELECT
[ID] AS id
,[Descricao] AS descricao
,[Url] AS url
FROM Sistemas
""";
        return db.query(sql, new BeanPropertyRowMapper<>(SistemaModel.class));
    }

    @Override
    public SistemaModel Obter(Integer sistemaId) {
        String sql = """
SELECT
[ID] AS id
,[Descricao] AS descricao
,[Url] AS url
FROM Sistemas
WHERE ID = :id
""";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", sistemaId);
        return db.query(sql, params, new BeanPropertyRowMapper<>(SistemaModel.class))
                .stream().findFirst().orElse(null);
    }

    @Override
    public void Deletar(Integer sistemaId) {
        String sql = "DELETE FROM Usuarios WHERE ID = :id";
        Map<String, Object> params = Map.of("id", sistemaId);
        db.update(sql, params);
    }
}
