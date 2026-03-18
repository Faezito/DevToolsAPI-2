package com.faezito.devToolsAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChaveAPIModel {
    public Integer id;
    public Integer usuarioId;
    public String hashChave;
    public String prefixo;
    public String nome;
    public boolean ativa;
    public LocalDateTime dataCriacao;
}
