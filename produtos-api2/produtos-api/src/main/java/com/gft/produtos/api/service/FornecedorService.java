package com.gft.produtos.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.model.Fornecedormini;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.repository.FornecedorRepository;
import com.gft.produtos.api.repository.FornecedorminiRepository;
import com.gft.produtos.api.service.exception.FornecedorNaoExistenteException;

@Service
public class FornecedorService {

		
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private FornecedorminiRepository fmr;
	

	public Fornecedor atualizar(Long id, Fornecedor fornecedor) {
		Fornecedor fornecedorAtualizado = buscarFornecedorPeloId(id);
		BeanUtils.copyProperties(fornecedor, fornecedorAtualizado, "id");
		
		Fornecedormini miniVelho = fmr.findById(id).orElse(null);
		Fornecedormini miniAtualizado = miniVelho;
		BeanUtils.copyProperties(miniVelho, miniAtualizado, "id");
		fmr.save(miniAtualizado);
		
		return fr.save(fornecedorAtualizado);
	}
		
				
	public Fornecedor buscarFornecedorPeloId(Long id) {
		Fornecedor fornecedorAtualizado = fr.findById(id).orElse(null);
		if (fornecedorAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return fornecedorAtualizado;
	}


	public void salvaFornecedorMini(Fornecedor fornecedor) {
		/*
		 * Instanciar Fornecedormini durante a instância de novo Fornecedor.
		 * 
		 * */
		Fornecedormini mini = new Fornecedormini(
				fornecedor.getId(), 
				fornecedor.getNome(), 
				fornecedor.getCnpj());
		
		fmr.save(mini);
	}


	public void manterProdutos(Long id, Fornecedor fornecedor) {
		/*
		 *Dentro de Atualização.
		 *Pra não perder a lista de produtos após a atualização.
		 * */
		Fornecedor f = fr.findById(id).orElse(null);
		
		//Se código do fornecedor não existeno cadastro
		if(f==null) {
			throw new FornecedorNaoExistenteException();	
		}
		List<Produto> listP = f.getProdutos();
		fornecedor.setProdutos(listP);
	}
	
		
}

