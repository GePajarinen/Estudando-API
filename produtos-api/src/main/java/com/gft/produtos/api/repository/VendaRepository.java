package com.gft.produtos.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Venda;

public interface VendaRepository  extends JpaRepository<Venda, Long>{

	
	List<Venda> findByCodigoContaining(Long codigo);

	public List<Venda> findAllByOrderByCodigoAsc();
	
	public List<Venda> findAllByOrderByCodigoDesc();


	Venda findByCliente(Cliente c);

	
}
