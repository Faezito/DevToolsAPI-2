package com.faezito.devToolsAPI.model.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoFechamentoDTO
{
    @Schema(description = "ID do Chamado, tipo int", example = "1")
    public int id;
    @Schema(description = "Status do chamado, 1 - Aberto, 2 - Em andamento, 3 - Concluído com sucesso, 4 - Encerrado sem sucesso")
    public int status = 3;
    @Schema(description = "Data de fechamento do chamado")
    public LocalDate dataFechamento = LocalDate.now();
    @Schema(description = "Data de alteração do chamado")
    public LocalDate dataAlteracao = LocalDate.now();
    @Schema(description = "Id do usuário que fechou o chamado", example = "1")
    public int usuarioId;
}
