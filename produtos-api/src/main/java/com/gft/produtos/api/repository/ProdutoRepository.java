package com.gft.produtos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{

}
