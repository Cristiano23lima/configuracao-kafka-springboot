package com.cristiano.vendas.services.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(setterPrefix = "set")
@NoArgsConstructor
public class VendaVO implements Serializable {
    @NotNull(message = "Nome do produto é obrigátorio")
    @NotBlank(message = "Nome do produto não pode ser vazio")
    private String nomeProduto;

    @NotNull(message = "Preço do produto é obrigátorio")
    private Double precoProduto;
    @NotNull(message = "Quantidade do produto é obrigátorio")
    private Integer quantidadeProduto;

    private String nomeCliente;
}
