package com.faezito.devToolsAPI.repository;

import com.faezito.devToolsAPI.model.ChamadoModel;
import com.faezito.devToolsAPI.repository.interfaces.IChamadoRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChamadoRepository implements IChamadoRepository {
    private final NamedParameterJdbcTemplate db;

    public ChamadoRepository(NamedParameterJdbcTemplate db) {
        this.db = db;
    }

    @Override
    public List<ChamadoModel> Listar(Integer sistemaId, Integer usuarioId, Integer atendenteId, Integer chamadoId) {
        String sql = """
                select * from dev.Chamados
                WHERE (:sistemaId IS NULL OR SistemaID = :sistemaId)
                AND   (:usuarioId IS NULL OR UsuarioID = :usuarioId)
                AND   (:atendenteId IS NULL OR AtendenteID = :atendenteId) 
                AND   (:chamadoId IS NULL OR ID = :chamadoId)                
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sistemaId", sistemaId)
                .addValue("usuarioId", usuarioId)
                .addValue("atendenteId", atendenteId)
                .addValue("chamadoId", chamadoId);
        return db.query(sql, params, new BeanPropertyRowMapper<>(ChamadoModel.class));
    }

    @Override
    public void Inserir(ChamadoModel model) {
        String sql = """
                INSERT INTO dev.Chamados
                (Titulo, Descricao, Status, Prioridade, UsuarioID, DataAbertura, UsuarioLogado)
                VALUES
                (:titulo, :descricao, :status, :prioridade, :usuarioId, :dataAbertura, :usuarioLogado)
                """;
        SqlParameterSource param = new BeanPropertySqlParameterSource(model);
        db.update(sql, param);
    }

    @Override
    public void Editar(ChamadoModel model) {
        String sql = """
                UPDATE dev.Chamados SET
                Titulo = :titulo,
                Descricao = :descricao,
                Status = :status,
                Prioridade = :prioridade,
                UsuarioID = :usuarioId,
                DataFechamento = :dataFechamento,
                DataAlteracao = :dataAlteracao
                """;

        SqlParameterSource param = new BeanPropertySqlParameterSource(model);
        db.update(sql, param);
    }

    @Override
    public void Excluir(Integer id) {
        String sql = "DELETE FROM dev.Chamados WHERE ID = :id";
        Map<String, Object> params = Map.of("id", id);
        db.update(sql, params);
    }

    @Override
    public void Atribuir(ChamadoModel model) {
        String sql = "UPDATE dev.Chamados SET AtendenteID = :atendenteId, UsuarioLogado = :usuarioLogado, DataAlteracao = :dataAlteracao WHERE ID = :id";
        SqlParameterSource param = new BeanPropertySqlParameterSource(model);
        db.update(sql, param);
    }
}
