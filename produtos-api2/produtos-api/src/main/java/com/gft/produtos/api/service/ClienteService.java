package com.gft.produtos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.repository.ClienteRepository;
import com.gft.produtos.api.service.exception.VendaClienteNaoExistenteException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cr;

	
	public Cliente atualizar(Long id, Cliente cliente) {
		
		Cliente clienteAtualizado = buscarClientePeloId(id);
		
		cliente.setDataCadastro(clienteAtualizado.getDataCadastro()); //Para a data na Atualização ficar igual à data do Cadastro.
		
		BeanUtils.copyProperties(cliente, clienteAtualizado, "id");
		return cr.save(clienteAtualizado);
	}

			
	public Cliente buscarClientePeloId(Long id) {
		Cliente clienteAtualizado = cr.findById(id).orElse(null);
		
		
		if (clienteAtualizado == null) {
			throw new VendaClienteNaoExistenteException();
		}
		return clienteAtualizado;
	}
	
}


