package com.faezito.devToolsAPI.model.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

public class ChamadoRequestDTO {
    @Schema(description = "ID do usuário que abriu o chamado, tipo int", example = "1")
    public Integer usuarioId;

    @Schema(description = "ID do sistema de onde o chamado veio, tipo int", example = "1")
    public Integer sistemaId;

    @Schema(description = "ID do atendente do chamado, tipo int", example = "1")
    public Integer atendenteId;

    @Schema(description = "ID do Chamado, tipo int", example = "1")
    public Integer chamadoId;
}