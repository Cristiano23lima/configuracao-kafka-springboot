package com.cristiano.vendas.util;

import com.cristiano.vendas.services.dto.EmailSendDTO;

public class EmailSendCreator {
    public static EmailSendDTO createEmailSendDto() {
        return EmailSendDTO.builder().setEmailDestino("devcristiano.rodrigues@gmail.com").setMensagem("Teste mensagem")
                .setNomeDestino("Teste").build();
    }
}
