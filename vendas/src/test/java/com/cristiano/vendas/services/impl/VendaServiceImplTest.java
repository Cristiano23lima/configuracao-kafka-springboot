package com.cristiano.vendas.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;

import com.cristiano.vendas.exceptions.BadRequestException;
import com.cristiano.vendas.models.Venda;
import com.cristiano.vendas.repositorys.VendaRepository;
import com.cristiano.vendas.services.dto.EmailSendDTO;
import com.cristiano.vendas.services.dto.VendaDTO;
import com.cristiano.vendas.services.mappers.VendaMapper;
import com.cristiano.vendas.services.vo.VendaVO;

import com.cristiano.vendas.util.VendaCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

	@Captor
	ArgumentCaptor<Venda> vendaCaptor;

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

		Mockito.verify(vendaRepositoryMock).save(vendaCaptor.capture());
		Venda vendaCaptorValue = vendaCaptor.getValue();

		assertEquals(vo.getNomeCliente(), vendaCaptorValue.getNomeCliente());
		assertEquals(vo.getNomeProduto(), vendaCaptorValue.getNomeProduto());
		assertEquals(vo.getPrecoProduto(), vendaCaptorValue.getPrecoProduto());
		assertEquals(vo.getQuantidadeProduto(), vendaCaptorValue.getQuantidadeProduto());

		Assertions.assertThat(venda).isNotNull();
		Assertions.assertThat(venda).isExactlyInstanceOf(VendaDTO.class);

	}

	@Test
	@DisplayName("Lançar um BadRequestException, caso campos obrigatorios estejam vazios ou nulos")
	public void salvarVenda_throwBadRequestException_quandoCamposObrigatoriosVazios() throws Exception {
		VendaVO vendaVo = VendaCreator.createVendaVoInvalido();
		Venda vendaInvalida = VendaCreator.createVendaAntesSalvarInvalida();

		BDDMockito.when(vendaMapperMock.toModel(vendaVo)).thenReturn(vendaInvalida);
		BDDMockito.when(vendaRepositoryMock.save(vendaInvalida)).thenThrow(BadRequestException.class);

		Mockito.verifyNoMoreInteractions(vendaRepositoryMock);

		Assertions.assertThatThrownBy(() -> this.vendaServiceImpl.salvarVenda(vendaVo))
				.isInstanceOf(BadRequestException.class);
	}

}
