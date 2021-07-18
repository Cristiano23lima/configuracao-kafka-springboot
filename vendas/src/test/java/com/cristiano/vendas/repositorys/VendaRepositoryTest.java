package com.cristiano.vendas.repositorys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cristiano.vendas.models.Venda;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@DisplayName("Testes para o repository de vendas")
class VendaRepositoryTest {

    @Autowired
    private VendaRepository vendaRepository;

    @Test
    @DisplayName("Salvar venda quando for sucesso")
    public void save_persistirVenda_quandoSucesso() {
        Venda vendaParaSalvar = createVenda();

        Venda vendaSalva = this.vendaRepository.save(vendaParaSalvar);
        Assertions.assertThat(vendaSalva).isNotNull();
        Assertions.assertThat(vendaSalva.getVendaId()).isNotNull().isInstanceOf(UUID.class);
        Assertions.assertThat(vendaSalva.getCreatedAt()).isNotNull().isExactlyInstanceOf(LocalDateTime.class);
        Assertions.assertThat(vendaSalva.getNomeCliente()).isNotNull().isEqualTo(vendaParaSalvar.getNomeCliente());
        Assertions.assertThat(vendaSalva.getNomeProduto()).isNotNull().isEqualTo(vendaParaSalvar.getNomeProduto());
        Assertions.assertThat(vendaSalva.getPrecoProduto()).isNotNaN().isEqualTo(vendaParaSalvar.getPrecoProduto());
        Assertions.assertThat(vendaSalva.getQuantidadeProduto()).isNotNull()
                .isEqualTo(vendaParaSalvar.getQuantidadeProduto());
    }

    @Test
    @DisplayName("Retorna exceção, ao tentar persistir uma venda que não tem nome do produto")
    public void save_retornarErroAoPersistirVendaSemNomeProduto_quandoError() {
        Venda vendaParaSalvar = createVenda();
        vendaParaSalvar.setNomeProduto(null);

        Assertions.assertThatThrownBy(() -> this.vendaRepository.save(vendaParaSalvar))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Retorna exceção, ao tentar persistir venda que não tem o preço do produto")
    public void save_retornarErroAoPersistirVendaSemValorProduto_quandoError() {
        Venda vendaParaSalvar = createVenda();
        vendaParaSalvar.setPrecoProduto(null);

        Assertions.assertThatThrownBy(() -> this.vendaRepository.save(vendaParaSalvar))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Retorna exceção, ao tentar persistir venda que não possui a quantidade de produtos")
    public void save_retornarErroAoPersistirVendaSemQuantidadeProduto_quandoError() {
        Venda vendaParaSalvar = createVenda();
        vendaParaSalvar.setQuantidadeProduto(null);

        Assertions.assertThatThrownBy(() -> this.vendaRepository.save(vendaParaSalvar))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Venda createVenda() {
        return Venda.builder().setNomeCliente("Cristiano Rodrigues").setNomeProduto("Recheado").setPrecoProduto(9.0)
                .setQuantidadeProduto(20).build();
    }

}
