package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Fornecedor;

public interface FornecedorRepository  extends JpaRepository<Fornecedor, Long>{

	List<Fornecedor> findByNomeContaining(String nome);

	public List<Fornecedor> findAllByOrderByNomeAsc();
	
	public List<Fornecedor> findAllByOrderByNomeDesc();

	Fornecedor findByCodigo(Long codigo);
}
