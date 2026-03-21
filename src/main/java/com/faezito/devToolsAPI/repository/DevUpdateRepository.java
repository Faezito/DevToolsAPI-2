package com.faezito.devToolsAPI.repository;

import com.faezito.devToolsAPI.model.DevUpdateModel;
import com.faezito.devToolsAPI.repository.interfaces.IDevUpdateRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DevUpdateRepository implements IDevUpdateRepository {
    private final NamedParameterJdbcTemplate db;

    public DevUpdateRepository(NamedParameterJdbcTemplate db) {
        this.db = db;
    }

    @Override
    public List<DevUpdateModel> Listar(Integer sistemaId) {
        String sql = "select * from dev.Atualizacoes where SistemaID = :sistemaId";

        Map<String, Object> params = Map.of("sistemaId", sistemaId);

        return db.query(sql, params, new BeanPropertyRowMapper<>(DevUpdateModel.class));
    }

    @Override
    public DevUpdateModel Obter(Integer id) {
        String sql = "select * from dev.Atualizacoes where ID = :id";

        Map<String, Object> params = Map.of("id", id);

        return db.query(sql, params, new BeanPropertyRowMapper<>(DevUpdateModel.class))
                .stream().findFirst().orElse(null);
    }

    @Override
    public void Inserir(DevUpdateModel devUpdateModel) {
        String sql = """
                INSERT INTO dev.Atualizacoes
                (
                    SistemaID,
                    Titulo,
                    Texto,
                    DataAtualizacao
                    ) VALUES
                    (
                    :sistemaId
                    ,:titulo
                    ,:texto
                    ,:dataAtualizacao
                    )
                """;

        SqlParameterSource param = new BeanPropertySqlParameterSource(devUpdateModel);
        db.update(sql,param);
    }

    @Override
    public void Editar(DevUpdateModel devUpdateModel) {
        String sql = """
                UPDATE dev.Atualizacoes
                SET Titulo = :titulo,
                    DataAtualizacao = :dataAtualizacao,
                    Texto = :texto,
                    SistemaID = :sistemaId
                WHERE ID = :id
        """;
        SqlParameterSource param = new BeanPropertySqlParameterSource(devUpdateModel);
        db.update(sql,param);
    }

    @Override
    public void Limpar(Integer sistemaId) {
        String sql = """
                WITH CTE_RANKING AS (
                    SELECT *,
                           ROW_NUMBER() OVER (
                               PARTITION BY SistemaID
                               ORDER BY DataAtualizacao DESC
                           ) AS Posicao
                    FROM dev.Atualizacoes
                    WHERE SistemaID = :sistemaId
                )
                DELETE FROM CTE_RANKING
                WHERE Posicao > 2
                  AND DataAtualizacao < DATEADD(MONTH, -3, GETDATE());
        """;

        SqlParameterSource param = new MapSqlParameterSource("sistemaId", sistemaId);
        db.update(sql,param);
    }

    @Override
    public void Excluir(Integer id) {
        String sql = "DELETE FROM dev.Atualizacoes WHERE ID = :id";
        Map<String, Object> params = Map.of("id", id);
        db.update(sql, params);
    }
}
