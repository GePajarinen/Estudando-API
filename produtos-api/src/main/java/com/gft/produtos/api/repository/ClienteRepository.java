package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

	
	List<Cliente> findByNomeContaining(String nome);

	public List<Cliente> findAllByOrderByNomeAsc();
	
	public List<Cliente> findAllByOrderByNomeDesc();
	
	public Cliente findByCodigo(Long codigo);

	Cliente findByNome(String string);
	
	
}
