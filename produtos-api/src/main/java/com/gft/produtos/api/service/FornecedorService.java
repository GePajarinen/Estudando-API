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

@Service
public class FornecedorService {

		
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private FornecedorminiRepository fmr;
	


	public Fornecedor atualizar(Long codigo, Fornecedor fornecedor) {
		Fornecedor fornecedorAtualizado = buscarFornecedorPeloCodigo(codigo);
		BeanUtils.copyProperties(fornecedor, fornecedorAtualizado, "codigo");
		
		Fornecedormini miniVelho = fmr.findAllByCodigo(codigo);
		Fornecedormini miniAtualizado = miniVelho;
		BeanUtils.copyProperties(miniVelho, miniAtualizado, "codigo");
		fmr.save(miniAtualizado);
		
		
		return fr.save(fornecedorAtualizado);
	}

		
				
	public Fornecedor buscarFornecedorPeloCodigo(Long codigo) {
		Fornecedor fornecedorAtualizado = fr.findById(codigo).orElse(null);
		if (fornecedorAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return fornecedorAtualizado;
	}



	public void salvaFornecedorMini(Fornecedor fornecedor) {
		Fornecedormini mini = new Fornecedormini(
				fornecedor.getCodigo(), 
				fornecedor.getNome(), 
				fornecedor.getCnpj());
		
		fmr.save(mini);
		
	}



	public void manterProdutos(Long codigo, Fornecedor fornecedor) {
		
		Fornecedor f = fr.findByCodigo(codigo);
		List<Produto> listP = f.getProdutos();
		fornecedor.setProdutos(listP);
		
	}
	
		
}

