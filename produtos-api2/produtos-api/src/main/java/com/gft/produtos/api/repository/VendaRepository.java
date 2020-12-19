package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Venda;

public interface VendaRepository  extends JpaRepository<Venda, Long>{
	
	public List<Venda> findAllByOrderByIdAsc();
	
	public List<Venda> findAllByOrderByIdDesc();

	Venda findByCliente(Cliente c);

	//List<Venda> findById(Long id);

	public List<Venda> findAllByCliente(Cliente c);

	
}
