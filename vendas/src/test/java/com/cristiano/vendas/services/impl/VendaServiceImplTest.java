package com.cristiano.vendas.services.impl;

import static org.mockito.ArgumentMatchers.eq;

import java.util.UUID;

import com.cristiano.vendas.models.Venda;
import com.cristiano.vendas.repositorys.VendaRepository;
import com.cristiano.vendas.services.dto.EmailSendDTO;
import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.mappers.VendaMapper;
import com.cristiano.vendas.services.vo.VendaVO;
import com.cristiano.vendas.util.EmailSendCreator;
import com.cristiano.vendas.util.VendaCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.bytebuddy.asm.Advice.Argument;

@ExtendWith(SpringExtension.class)
public class VendaServiceImplTest {
	@InjectMocks
	private VendaServiceImpl vendaServiceImpl;

	@Mock
	private VendaRepository vendaRepositoryMock;

	@Mock
	private VendaMapper vendaMapperMock;

	@Mock
	private ObjectMapper objectMapperMock;

	@Mock
	private KafkaTemplate<String, String> kafkaTemplateMock;

	@Test
	@DisplayName("Retornar um dto do dado persistido no banco, quando der tudo certo")
	public void salvarVenda_retornaDtoVenda_quandoSucesso() throws Exception {
		BDDMockito.when(vendaMapperMock.toModel(ArgumentMatchers.any(VendaVO.class)))
				.thenReturn(VendaCreator.createVendaAntesSalvar());
		BDDMockito.when(vendaMapperMock.toDto(ArgumentMatchers.any(Venda.class))).thenReturn(VendaCreator.toDtoVenda());
		BDDMockito.when(vendaRepositoryMock.save(ArgumentMatchers.any(Venda.class)))
				.thenReturn(VendaCreator.createVendaValida());
		BDDMockito.when(objectMapperMock.writeValueAsString(ArgumentMatchers.any(EmailSendDTO.class)))
				.thenReturn(ArgumentMatchers.anyString());

		VendaVO vo = VendaCreator.toVOVenda();

		VendaDTO venda = this.vendaServiceImpl.salvarVenda(vo);

		Assertions.assertThat(venda).isNotNull();
		Assertions.assertThat(venda).isExactlyInstanceOf(VendaDTO.class);
	}

	@Test
	@DisplayName("Lançar uma exceção, caso campos obrigatorios estejam vazios ou nulos")
	public void salvarVenda_throwException_quandoCamposObrigatoriosVazios() throws Exception {
		VendaVO vendaVo = VendaCreator.createVendaVoInvalido();

		BDDMockito.when(vendaMapperMock.toModel(vendaVo)).thenThrow(RuntimeException.class);

		Assertions.assertThatThrownBy(() -> this.vendaServiceImpl.salvarVenda(vendaVo))
				.isInstanceOf(RuntimeException.class);
	}

}
