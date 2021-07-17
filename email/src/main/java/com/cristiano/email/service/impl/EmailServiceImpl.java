package com.cristiano.email.service.impl;

import com.cristiano.email.model.EmailSend;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl {
    private final JavaMailSender emailSender;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "enviar-email", groupId = "Email")
    public void sendEmail(String mensagemKafka) throws Exception {
        this.enviarEmail(this.getModel(mensagemKafka));
        System.out.println(mensagemKafka);
    }

    public void enviarEmail(EmailSend email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("botdellead@gmail.com");
        mailMessage.setText(email.getMensagem());
        mailMessage.setTo(email.getEmailDestino());
        mailMessage.setSubject("STATUS DE VENDA");

        this.emailSender.send(mailMessage);
    }

    public EmailSend getModel(String modelString) throws Exception {
        try {
            return this.objectMapper.readValue(modelString, EmailSend.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
