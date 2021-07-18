package com.cristiano.vendas.services.impl;

import java.rmi.ServerError;
import java.util.UUID;

import javax.validation.Valid;

import com.cristiano.vendas.exceptions.BadRequestException;
import com.cristiano.vendas.models.Venda;
import com.cristiano.vendas.repositorys.VendaRepository;
import com.cristiano.vendas.services.VendaService;
import com.cristiano.vendas.services.dto.EmailSendDTO;
import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.mappers.VendaMapper;
import com.cristiano.vendas.services.vo.VendaVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public VendaDTO salvarVenda(VendaVO venda) throws Exception {
        Venda vendaASerSalva = this.vendaMapper.toModel(venda);

        Venda vendaSalva = new Venda();

        try {
            vendaSalva = this.vendaRepository.save(vendaASerSalva);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        this.enviarMsgEmail(EmailSendDTO.builder().setEmailDestino("dj7cristiano@gmail.com")
                .setMensagem("Venda cadastrada com sucesso").setNomeDestino("CRISTIANO RODRIGUES DE LIMA").build());

        return this.vendaMapper.toDto(vendaSalva);
    }

    private void enviarMsgEmail(EmailSendDTO email) throws Exception {
        this.kafkaTemplate.send("enviar-email", UUID.randomUUID().toString(), this.getString(email));
    }

    private String getString(EmailSendDTO email) throws Exception {
        try {
            return objectMapper.writeValueAsString(email);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
