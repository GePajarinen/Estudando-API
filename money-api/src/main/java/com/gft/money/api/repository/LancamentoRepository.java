package com.gft.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.money.api.model.Lancamento;
import com.gft.money.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

	
	
	
}
