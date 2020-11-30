package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.repository.produto.ProdutoRepositoryQuery;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery{

	
	List<Produto> findByNomeContaining(String nome);
	
	//public Page<Produto> findByNomeContaining(String nome);

}
