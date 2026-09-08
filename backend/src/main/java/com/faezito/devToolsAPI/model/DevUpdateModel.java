package com.faezito.devToolsAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevUpdateModel {
    public Integer id;
    public Integer sistemaId;
    public String titulo;
    public String texto;
    public LocalDateTime dataAtualizacao;
}
