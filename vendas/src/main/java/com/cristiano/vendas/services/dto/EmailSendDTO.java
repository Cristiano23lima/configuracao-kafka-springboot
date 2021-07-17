package com.cristiano.vendas.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class EmailSendDTO {
    private String nomeDestino;
    private String emailDestino;
    private String mensagem;
}
