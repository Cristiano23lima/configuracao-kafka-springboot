package com.cristiano.vendas.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import com.cristiano.vendas.services.VendaService;
import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.vo.VendaVO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;

    @PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendaDTO> salvarVenda(@RequestBody VendaVO venda) throws Exception {
        return ResponseEntity.created(new URI("/salvar")).body(vendaService.salvarVenda(venda));
    }
}
