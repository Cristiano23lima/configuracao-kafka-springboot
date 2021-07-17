package com.cristiano.vendas.services.mappers;

import com.cristiano.vendas.models.Venda;
import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.vo.VendaVO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendaMapper {
    Venda toModel(VendaDTO dto);

    VendaDTO toDto(Venda model);

    Venda toModel(VendaVO form);
}
