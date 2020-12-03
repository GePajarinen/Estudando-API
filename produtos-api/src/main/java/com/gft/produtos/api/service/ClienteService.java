package com.gft.produtos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.repository.ClienteRepository;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository cr;

	public Cliente atualizar(Long codigo, Cliente cliente) {
		
		Cliente clienteAtualizado = buscarClientePeloCodigo(codigo);
		
		cliente.setdatacadastro(clienteAtualizado.getdatacadastro());
		
		BeanUtils.copyProperties(cliente, clienteAtualizado, "codigo");
		return cr.save(clienteAtualizado);
	}

			
	public Cliente buscarClientePeloCodigo(Long codigo) {
		Cliente clienteAtualizado = cr.findById(codigo).orElse(null);
		if (clienteAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return clienteAtualizado;
	}
	
	
}


