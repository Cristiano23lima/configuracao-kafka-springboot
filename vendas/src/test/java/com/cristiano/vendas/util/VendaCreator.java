package com.cristiano.vendas.util;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cristiano.vendas.models.Venda;
import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.vo.VendaVO;

public class VendaCreator {
    public static Venda createVendaValida() {
        return Venda.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto("Recheado").setPrecoProduto(9.0)
                .setCreatedAt(LocalDateTime.now()).setVendaId(UUID.randomUUID()).setQuantidadeProduto(20).build();
    }

    public static VendaVO createVendaVoInvalido() {
        return VendaVO.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto(null).setPrecoProduto(9.0)
                .setQuantidadeProduto(null).build();
    }

    public static Venda createVendaAntesSalvar() {
        return Venda.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto("Recheado").setPrecoProduto(9.0)
                .setQuantidadeProduto(20).build();
    }

    public static Venda createVendaAntesSalvarInvalida() {
        return Venda.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto(null).setPrecoProduto(9.0)
                .setQuantidadeProduto(null).setCreatedAt(null).setUpdatedAt(null).setVendaId(null).build();
    }

    public static VendaVO toVOVenda() {
        return VendaVO.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto("Recheado").setPrecoProduto(9.0)
                .setQuantidadeProduto(20).build();
    }

    public static VendaDTO toDtoVenda() {
        return VendaDTO.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto("Recheado").setPrecoProduto(9.0)
                .setCreatedAt(LocalDateTime.now()).setVendaId(UUID.randomUUID()).setQuantidadeProduto(20).build();
    }
}
