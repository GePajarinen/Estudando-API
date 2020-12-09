package com.gft.produtos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Fornecedormini;

public interface FornecedorminiRepository  extends JpaRepository<Fornecedormini, Long> {



	Fornecedormini findByCodigo(Long codigo);

}
