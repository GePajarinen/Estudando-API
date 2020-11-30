package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{

	
	List<Produto> findByNomeContaining(String nome);
	
	//public Page<Produto> findByNomeContaining(String nome);

}
