package com.cristiano.email.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder(setterPrefix = "set")
@AllArgsConstructor
public class EmailSend implements Serializable {
    private String nomeDestino;
    private String emailDestino;
    private String mensagem;
}
