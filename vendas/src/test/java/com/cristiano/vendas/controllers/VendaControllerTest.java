package com.cristiano.vendas.controllers;

import com.cristiano.vendas.services.vo.VendaVO;
import com.cristiano.vendas.util.VendaCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VendaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Retorna o dto da venda salva")
	void salvarVenda_retornaVendaSalva_quandoSucesso() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/salvar").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(VendaCreator.toVOVenda())))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Retorna BadRequestException ao VO possui dados inválidos")
	void salvarVenda_throwBadRequestException_quandoDadosObrigatoriosNulos() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/salvar").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(new VendaVO())))
				.andExpect(status().isBadRequest());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
