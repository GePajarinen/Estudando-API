package com.gft.produtos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.VendaRepository;


@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vr;

	public Venda atualizar(Long codigo, Venda venda) {
		Venda vendaAtualizada = buscarVendaPeloCodigo(codigo);
		BeanUtils.copyProperties(venda, vendaAtualizada, "codigo");
		return vr.save(vendaAtualizada);
	}

	
		
	public Venda buscarVendaPeloCodigo(Long codigo) {
		Venda vendaAtualizada = vr.findById(codigo).orElse(null);
		if (vendaAtualizada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendaAtualizada;
	}
	
	
}

