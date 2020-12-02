package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.model.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{

	
	List<Produto> findByNomeContaining(String nome);

	public List<Produto> findAllByOrderByNomeAsc();
	
	public List<Produto> findAllByOrderByNomeDesc();

	public Produto findByFornecedor(Fornecedor fornecedor);

	List<Produto> findAllByFornecedor(Fornecedor fornecedor);

	Produto findByCodigo(Long codigo);
	
	//public Page<Produto> findByNomeContaining(String nome);

}
