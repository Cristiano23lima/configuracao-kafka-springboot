package com.cristiano.vendas.repositorys;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cristiano.vendas.models.Venda;
import com.cristiano.vendas.util.VendaCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("Testes para o repository de vendas")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ImportAutoConfiguration
class VendaRepositoryTest {

    @Autowired
    private VendaRepository vendaRepository;

    @Test
    @DisplayName("Salvar venda quando for sucesso")
    public void save_persistirVenda_quandoSucesso() {
        Venda vendaParaSalvar = VendaCreator.createVendaAntesSalvar();

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
    @DisplayName("Retorna exce????o, ao tentar persistir uma venda que n??o tem nome do produto")
    public void save_throwDataIntegrityViolationException_quandoVendaSemNomeProduto() {
        Venda vendaParaSalvar = VendaCreator.createVendaAntesSalvar();
        vendaParaSalvar.setNomeProduto(null);

        Assertions.assertThatThrownBy(() -> this.vendaRepository.save(vendaParaSalvar))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Retorna exce????o, ao tentar persistir venda que n??o tem o pre??o do produto")
    public void save_throwDataIntegrityViolationException_quandoVendaSemValorProduto() {
        Venda vendaParaSalvar = VendaCreator.createVendaAntesSalvar();
        vendaParaSalvar.setPrecoProduto(null);

        Assertions.assertThatThrownBy(() -> this.vendaRepository.save(vendaParaSalvar))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Retorna exce????o, ao tentar persistir venda que n??o possui a quantidade de produtos")
    public void save_throwDataIntegrityViolationException_quandoSemQuantidadeProduto() {
        Venda vendaParaSalvar = VendaCreator.createVendaAntesSalvar();
        vendaParaSalvar.setQuantidadeProduto(null);

        Assertions.assertThatThrownBy(() -> this.vendaRepository.save(vendaParaSalvar))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
