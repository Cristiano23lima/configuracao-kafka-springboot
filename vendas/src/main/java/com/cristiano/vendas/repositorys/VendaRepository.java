package com.cristiano.vendas.repositorys;

import java.util.UUID;

import com.cristiano.vendas.models.Venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {
}
