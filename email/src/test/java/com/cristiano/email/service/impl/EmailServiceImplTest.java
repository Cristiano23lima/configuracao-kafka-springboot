package com.cristiano.email.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cristiano.email.model.EmailSend;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class EmailServiceImplTest {
    @InjectMocks
    private EmailServiceImpl emailServiceImplMock;

    @Mock
    private JavaMailSender javaMailSenderMock;

    @Mock
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageCaptor;

    @Test
    @DisplayName("Enviar email, caso não haja nenhum problema.")
    void enviarEmail_enviarUmEmail_casoSejaSucesso() {
        EmailSend emailParaEnviar = this.createEmailSend();

        this.emailServiceImplMock.enviarEmail(emailParaEnviar);

        doNothing().when(this.javaMailSenderMock).send(any(SimpleMailMessage.class));

        Mockito.verify(this.javaMailSenderMock, times(1)).send(simpleMailMessageCaptor.capture());

        SimpleMailMessage argumentPassed = simpleMailMessageCaptor.getValue();

        assertEquals(argumentPassed.getFrom(), "botdellead@gmail.com");
        assertEquals(argumentPassed.getText(), emailParaEnviar.getMensagem());
        Assertions.assertThat(argumentPassed.getTo()).isNotEmpty().isNotNull()
                .contains(emailParaEnviar.getEmailDestino());
        assertEquals(argumentPassed.getSubject(), "STATUS DE VENDA");
    }

    private EmailSend createEmailSend() {
        return EmailSend.builder().setEmailDestino("dj7cristiano@gmail.com").setMensagem("Criando os testes")
                .setNomeDestino("Cristiano Rodrigues").build();
    }

}
