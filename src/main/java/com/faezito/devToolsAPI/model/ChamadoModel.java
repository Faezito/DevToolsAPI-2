package com.faezito.devToolsAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamadoModel {
    @Schema(description = "ID do Chamado, tipo int", example = "1")
    private Integer id;
    @Schema(description = "ID do sistema do qual o chamado veio", example = "1")
    private Integer sistemaId;
    @Schema(description = "ID do responsável por atender o chamado", example = "1")
    private Integer atendenteId;
    @Schema(description = "Título do chamado, string")
    private String titulo;
    @Schema(description = "Descrição/corpo do chamado, string 500")
    private String descricao;
    @Schema(description = "Status do chamado, 1 - Aberto, 2 - Em andamento, 3 - Concluído com sucesso, 4 - Encerrado sem sucesso")
    private Integer status;
    @Schema(description = "Prioridade do chamado: 1 - Alta, 2 - Média, 3 -Baixa")
    private Integer prioridade;
    @Schema(description = "ID int do usuário que abriu o chamado")
    private Integer usuarioId;
    @Schema(description = "Data de abertura do chamado")
    private LocalDateTime dataAbertura;
    @Schema(description = "Data de encerramento do chamado")
    private LocalDateTime dataFechamento;
    @Schema(description = "Usuario que criou o chamado no sistema")
    private Integer usuarioLogado;
    @Schema(description = "Data na qual o chamado foi alterado")
    private LocalDateTime dataAlteracao;
}
