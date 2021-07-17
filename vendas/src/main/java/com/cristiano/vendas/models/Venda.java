package com.cristiano.vendas.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "venda")
@Getter
@Setter
@NoArgsConstructor
@Builder(setterPrefix = "set")
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "venda_id", unique = true, nullable = false)
    private UUID vendaId;

    @Column(name = "nome_produto", columnDefinition = "VARCHAR(100)", nullable = false)
    private String nomeProduto;

    @Column(name = "preco_produto", precision = 2, columnDefinition = "decimal", nullable = false)
    private Double precoProduto;

    @Column(name = "qtd_produto", columnDefinition = "INTEGER", nullable = false)
    private Integer quantidadeProduto;

    @Column(name = "nome_cliente", columnDefinition = "VARCHAR(100)")
    private String nomeCliente;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
