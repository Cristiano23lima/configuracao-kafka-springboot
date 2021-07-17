package com.cristiano.vendas.services.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO implements Serializable {
    private String nomeProduto;
    private Double precoProduto;
    private Integer quantidadeProduto;
    private String nomeCliente;
    private UUID vendaId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
