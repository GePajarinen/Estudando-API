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

	//Faltava um endpoint pra esse
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


	public void addProdutoEmFornecedor( Produto produto) {
		
		System.out.println("FORNECEDOR "+produto.getFornecedor().getCodigo() );
				
		if (produto.getFornecedor().getCodigo() == null) {
			throw new FornecedorVazioException();
		}
		
		Fornecedor fornecedor = fr.findByCodigo(produto.getFornecedor().getCodigo());
		
		if (fornecedor == null) {
			throw new FornecedorNaoExistenteException();
		}
		
		List<Produto> listaP = fornecedor.getProdutos();
		listaP.add(produto);
		
	}

	public void addFornecedorMiniDoProduto(Produto produto) {
		
		Fornecedormini fmini = fmr.findAllByCodigo(produto.getFornecedor().getCodigo());
		produto.setFornecedor(fmini);
		
	}

	public void retirarDaListaDoFornecedor(Long codigo) {
		
		
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
	
	
}
