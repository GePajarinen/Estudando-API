package com.gft.produtos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository pr;

	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoAtualizado = buscarProdutoPeloCodigo(codigo);
		BeanUtils.copyProperties(produto, produtoAtualizado, "codigo");
		return pr.save(produtoAtualizado);
	}

	//Quando tenta atualizar a PROMOCAO de alguém que não existe, ele manda o código como NULL
	public void atualizarPromocao(Long codigo, Boolean promocao) {
		Produto produtoAtualizado = pr.findById(codigo).orElse(null);
		if (produtoAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		produtoAtualizado.setPromocao(promocao);
		pr.save(produtoAtualizado);
	}
	
	public Produto buscarProdutoPeloCodigo(Long codigo) {
		Produto produtoAtualizado = pr.findById(codigo).orElse(null);
		if (produtoAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoAtualizado;
	}
	
	
}
