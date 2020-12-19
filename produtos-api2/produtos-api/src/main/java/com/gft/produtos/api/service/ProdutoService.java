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
import com.gft.produtos.api.repository.ProdutoRepository;
import com.gft.produtos.api.service.exception.FornecedorNaoExistenteException;
import com.gft.produtos.api.service.exception.FornecedorVazioException;
import com.gft.produtos.api.service.exception.ProdutoNaoExistenteException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private FornecedorminiRepository fmr;
	
	
	public Produto atualizar(Long id, Produto produto) {
		Produto produtoAtualizado = buscarProdutoPeloId(id);
		BeanUtils.copyProperties(produto, produtoAtualizado, "id");
		return pr.save(produtoAtualizado);
	}

	
	public void atualizarPromocao(Long id, Boolean promocao) {
		/*
		 * Para atualziar só o status da Promoção em PRODUTO
		 * */
				
		Produto produtoAtualizado = pr.findById(id).orElse(null);
		//Se mandar id inválido
		if (produtoAtualizado == null) {
			throw new ProdutoNaoExistenteException("O Produto "+ id + " não consta no cadastro.");
		}
		
		produtoAtualizado.setPromocao(promocao);
		pr.save(produtoAtualizado);
	}
	
	
	public Produto buscarProdutoPeloId(Long id) {
		Produto produtoAtualizado = pr.findById(id).orElse(null);
		if (produtoAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoAtualizado;
	}


	public void addProdutoEmFornecedor(Produto produto) {
		
		if (produto.getFornecedor().getId() == null) {//Se Fornecedor estiver vazio
			throw new FornecedorVazioException();
		}
		
		Fornecedor fornecedor = fr.findById(produto.getFornecedor().getId()).orElse(null);
		
		if (fornecedor == null) { //Se Fornecedor não existe no cadastro
			throw new FornecedorNaoExistenteException();
		}
		
		List<Produto> listaP = fornecedor.getProdutos();
		listaP.add(produto);
	}

	
	public void addFornecedorMiniDoProduto(Produto produto) {
		
		Fornecedormini fmini = fmr.findById(produto.getFornecedor().getId()).orElse(null);
		produto.setFornecedor(fmini);
	}

	
	public void retirarDaListaDoFornecedor(Long id) {
		/*
		 * Retirar produto deletado da lista do Fornecedor
		 * */
		
		Produto p = pr.findById(id).orElse(null);
		if (p == null) { 
			throw new ProdutoNaoExistenteException("O Produto de id " +
													id + " não existe no cadastro.");
		}
		
		System.out.println("pCODIGO"+ p.getId());
		
		Fornecedor f = fr.findById(p.getFornecedor().getId()).orElse(null);
		System.out.println("FORN "+ p.getFornecedor().getId());
		
		List<Produto> listaP = f.getProdutos();
		System.out.println("LISTA ANTES "+listaP.size());
		
		listaP.remove(p);
		System.out.println("LISTA DEPOIS "+listaP.size());
		
	}

	
	public void verificarFornecedor(Produto produto) { 
		/*
		 * Verificando Fornecedor dentro da ATUALIZAÇÃO.
		 * E devolver os dados compeltos do Fornecedormini
		 * para dentro de produto Atualizado.
		 * */
		
		Fornecedormini f = fmr.findById(produto.getFornecedor().getId()).orElse(null);
		if (f==null) { 
			throw new FornecedorNaoExistenteException();
		}
		
		produto.setFornecedor(f);
		
	}
	
	
}
