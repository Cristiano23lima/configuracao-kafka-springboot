package com.cristiano.email.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class EmailServiceImplTest {
    @InjectMocks
    private EmailServiceImpl emailServiceImpl;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private ObjectMapper objectMapper;

}
