package com.gft.produtos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.repository.FornecedorRepository;

@Service
public class FornecedorService {

		
	@Autowired
	private FornecedorRepository fr;
	


	public Fornecedor atualizar(Long codigo, Fornecedor fornecedor) {
		Fornecedor fornecedorAtualizado = buscarFornecedorPeloCodigo(codigo);
		BeanUtils.copyProperties(fornecedor, fornecedorAtualizado, "codigo");
		return fr.save(fornecedorAtualizado);
	}

		
				
	public Fornecedor buscarFornecedorPeloCodigo(Long codigo) {
		Fornecedor fornecedorAtualizado = fr.findById(codigo).orElse(null);
		if (fornecedorAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return fornecedorAtualizado;
	}
	
	
	/*public void procurandoListaDePordutos(Fornecedor f) {
		
		
		List <Produto> listaProdutos = pr.findAllByFornecedorCodigo(f.getCodigo());
		f.setProdutos(listaProdutos);
		
	}*/
		
		
}

