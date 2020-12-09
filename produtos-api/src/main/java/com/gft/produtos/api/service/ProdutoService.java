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

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private FornecedorminiRepository fmr;
	
	
	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoAtualizado = buscarProdutoPeloCodigo(codigo);
		BeanUtils.copyProperties(produto, produtoAtualizado, "codigo");
		return pr.save(produtoAtualizado);
	}

	
	public void atualizarPromocao(Long codigo, Boolean promocao) {
		/*
		 * Para atualziar só o status da Promoção em PRODUTO
		 * */
		
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


	public void addProdutoEmFornecedor( Produto produto) {
		
		if (produto.getFornecedor().getCodigo() == null) {//Se Fornecedor estiver vazio
			throw new FornecedorVazioException();
		}
		
		Fornecedor fornecedor = fr.findByCodigo(produto.getFornecedor().getCodigo());
		
		if (fornecedor == null) { //Se Fornecedor não existe no cadastro
			throw new FornecedorNaoExistenteException();
		}
		
		List<Produto> listaP = fornecedor.getProdutos();
		listaP.add(produto);
	}

	
	public void addFornecedorMiniDoProduto(Produto produto) {
		
		Fornecedormini fmini = fmr.findByCodigo(produto.getFornecedor().getCodigo());
		produto.setFornecedor(fmini);
	}

	
	public void retirarDaListaDoFornecedor(Long codigo) {
		/*
		 * Retirar produto deletado da lista do Fornecedor
		 * */
		
		Produto p = pr.findByCodigo(codigo);
		if (p == null) { 
			throw new EmptyResultDataAccessException(1);
		}
		
		System.out.println("pCODIGO"+ p.getCodigo());
		
		Fornecedor f = fr.findByCodigo(p.getFornecedor().getCodigo());
		System.out.println("FORN "+ p.getFornecedor().getCodigo());
		
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
		
		Fornecedormini f = fmr.findByCodigo(produto.getFornecedor().getCodigo());
		if (f==null) { 
			throw new FornecedorNaoExistenteException();
		}
		
		List<Fornecedor> listaF = fr.findAll();
		
		System.out.println(listaF.size());
		
		//Tentando mudar o fornecedor se houver troca
		/* 
		for(Fornecedor fornecedor : listaF) {
			List<Produto> produtos = fornecedor.getProdutos();
			if (produtos.contains(produto)) {
				System.out.println("TEM O PRODUTO!!!");
				produtos.remove(produto);
			}
		}
		
		//Adicioando o produto em outro fornecedor, se houve troca
		Fornecedor fornecedorReal = fr.findByCodigo(f.getCodigo());
		List<Produto> listP = fornecedorReal.getProdutos();
		listP.add(produto);*/
		
			
		produto.setFornecedor(f);
		
	}
	
	
}
