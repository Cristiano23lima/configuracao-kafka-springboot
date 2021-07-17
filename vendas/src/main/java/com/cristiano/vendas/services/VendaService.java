package com.cristiano.vendas.services;

import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.vo.VendaVO;

public interface VendaService {
    public VendaDTO salvarVenda(VendaVO venda) throws Exception;
}
