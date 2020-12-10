package com.gft.produtos.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.CadastroVenda;
import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.model.Fornecedormini;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.model.ProdutoListagem;
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.ClienteRepository;
import com.gft.produtos.api.repository.FornecedorRepository;
import com.gft.produtos.api.repository.FornecedorminiRepository;
import com.gft.produtos.api.repository.ProdutoRepository;
import com.gft.produtos.api.repository.VendaRepository;
import com.gft.produtos.api.service.exception.ClienteNaoIndicadoException;
import com.gft.produtos.api.service.exception.CodigoProdutoNaoIndicadoExecption;
import com.gft.produtos.api.service.exception.FornecedorNaoContemProdutoSelecionadoException;
import com.gft.produtos.api.service.exception.FornecedorNaoExistenteException;
import com.gft.produtos.api.service.exception.FornecedorVazioException;
import com.gft.produtos.api.service.exception.ListaDeProdutosVaziaException;
import com.gft.produtos.api.service.exception.ProdutoNaoExistenteException;
import com.gft.produtos.api.service.exception.VendaClienteNaoExistenteException;


@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vr;
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private ClienteRepository cr;
	
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private FornecedorminiRepository fmr;
	
	
	public void codigoClienteNull(Long codigo) {
		if(codigo == null) {
			throw new ClienteNaoIndicadoException();
		}
	}
	
	public void clienteNoCadastro(Cliente cliente) {
		if (cliente == null) {
			throw new VendaClienteNaoExistenteException();
		}
	}
	
		
	public Venda criarVenda(CadastroVenda cadastroVenda, List<Produto> listaProdutos) {
		/*
		 * Instanciar VENDA através do DTO CadastroVenda
		 * */
		
		//Se Cliente.codigo for NULL
		codigoClienteNull(cadastroVenda.getCliente().getCodigo());
		
		Cliente cliente = cr.findByCodigo(cadastroVenda.getCliente().getCodigo());
		
		//Se Cliente não consta no cadastro
		clienteNoCadastro(cliente);
		
		BigDecimal  total = somandoValoresProdutos(listaProdutos);
		
		Fornecedormini fornecedor = fmr.findByCodigo(cadastroVenda.getFornecedor().getCodigo());
		
		Venda venda = new Venda(cadastroVenda.getCodigo(), total, cadastroVenda.getDataVenda(), cliente, fornecedor, listaProdutos);
		
		return venda;
	}
	

	public Venda atualizar(Long codigo, CadastroVenda cadastroVenda) {
		Venda vendaAtualizada = buscarVendaPeloCodigo(codigo);
		
		//Se Cliente.codigo for NULL
		codigoClienteNull(cadastroVenda.getCliente().getCodigo());
		
		Cliente c = cr.findByCodigo(cadastroVenda.getCliente().getCodigo());
		
		//Se Cliente não consta no cadastro
		clienteNoCadastro(c);
		
		vendaAtualizada.setCliente(c);
		
		vendaAtualizada.setdatacompra(cadastroVenda.getDataVenda());
		
		List<Produto> newlistP = criarListaProdutos(cadastroVenda);
		vendaAtualizada.setProdutos(newlistP);
		
		//Verificando fornecedor
		temFornecedormini(cadastroVenda.getFornecedor());
		
		Fornecedormini f = fmr.findByCodigo(cadastroVenda.getFornecedor().getCodigo());
		vendaAtualizada.setFornecedor(f);
		
		vendaAtualizada.setValor(somandoValoresProdutos(newlistP));
		
		return vr.save(vendaAtualizada);
	}

		
	public Venda buscarVendaPeloCodigo(Long codigo) {
		Venda vendaAtualizada = vr.findById(codigo).orElse(null);
		
		if (vendaAtualizada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendaAtualizada;
	}

	
	private void temFornecedormini(Fornecedormini fmini) {
				
			if(fmini.getCodigo() == null) {
				throw new FornecedorVazioException();
			}
			
			Fornecedormini f = fmr.findByCodigo(fmini.getCodigo());
			
			if(f ==null) {
				throw new FornecedorNaoExistenteException();
			}
	}


	private BigDecimal somandoValoresProdutos(List<Produto> listaProdutos) {
		
		List<Produto> listaDeProdutos = listaProdutos; 
		
		//Somando os valores dos produtos
		BigDecimal  total = new BigDecimal("0.00");
		
		for (Produto produto : listaDeProdutos) {
		
			if(produto.getPromocao()) {
				total= total.add(produto.getvalorpromo());
				System.out.println("total " + total);
			}
			else {
				total = total.add(produto.getValor());
				System.out.println("total " + total);
			}
			
		}
		return total;
	}

	
	public List<Produto> criarListaProdutos(@Valid CadastroVenda cadastroVenda) {
		/*
		 * Criando a lista de Produtos à partir da lista DTO ProdutosListagem 
		 * que veio de CadastroVenda
		 * */
		
		List<Produto> lp = new ArrayList<Produto>();
		List<ProdutoListagem> listagem = cadastroVenda.getProdutos();
		
		//Se Lista de Produtos Estiver vazia
		if (listagem.size() == 0) {
			throw new ListaDeProdutosVaziaException();
		}
		
		for(ProdutoListagem n : listagem) {
			
			//Se código produto for NULL
			if (n.getCodigo() == null) {
				throw new CodigoProdutoNaoIndicadoExecption();
			}
			
			Produto p = pr.findByCodigo(n.getCodigo());
			
			//Se Produto não consta no cadastro
			if(p == null) {
				throw new ProdutoNaoExistenteException("O Produto de código " +
						n.getCodigo() + " não existe no cadastro.");
			}
			lp.add(p);
		}
		return lp;
	}
	
	
	public void validandoFornecedoresEProdutos(CadastroVenda cadastroVenda, List<Produto> listaP) {
		/*
		 * Se Fornecedor indicado na Venda não tem algum dos Produtos da Venda
		 * */
		
		temFornecedormini(cadastroVenda.getFornecedor());
		
		Fornecedor f = fr.findByCodigo(cadastroVenda.getFornecedor().getCodigo());
		if (f != null) {
			for(Produto p: listaP) {//Passando por cada produto da lista de venda
					
				if(!f.getProdutos().contains(p)) {
					throw new FornecedorNaoContemProdutoSelecionadoException(
						"Fornecedor "+ f.getCodigo()+ " não tem o produto: "
					+ p.getCodigo() );}
			}
		}
		else {//Se fornecedor não exite no cadastro.
			throw new FornecedorNaoExistenteException();
		}
		
	}


	public List<Venda> procurandoPeloNomeCliente(String nome) {
		List<Cliente> listC = cr.findByNomeContaining(nome);
		List<Venda> listV = new ArrayList<Venda>();
			
		for(Cliente c : listC) {
			listV.addAll(vr.findAllByCliente(c));
		}
		return listV;
	}

	
}

