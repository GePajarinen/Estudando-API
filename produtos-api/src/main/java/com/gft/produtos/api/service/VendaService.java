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
import com.gft.produtos.api.service.exception.FornecedorNaoContemProdutoSelecionadoException;
import com.gft.produtos.api.service.exception.FornecedorNaoExistenteException;
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
	
	
	public Venda atualizar(Long codigo, CadastroVenda cadastroVenda) {
		Venda vendaAtualizada = buscarVendaPeloCodigo(codigo);
		
		Cliente c = cr.findByCodigo(cadastroVenda.getCliente().getCodigo());
		if (c == null) {
			throw new VendaClienteNaoExistenteException();
		}
		vendaAtualizada.setCliente(c);
		
		vendaAtualizada.setdatacompra(cadastroVenda.getDataVenda());
		
		List<Produto> newlistP = criarListaProdutos(cadastroVenda);
		vendaAtualizada.setProdutos(newlistP);
		
		List<Fornecedormini> newlistF = listandoFornecedoresmini(newlistP);
		vendaAtualizada.setFornecedores(newlistF);
		
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

	
	public Venda criarVenda(CadastroVenda cadastroVenda, List<Produto> listaProdutos) {
		/*
		 * Instanciar VENDA através do DTO CadastroVenda
		 * */
		
		//Se Cliente.codigo for NULL
		if (cadastroVenda.getCliente().getCodigo() == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Cliente cliente = cr.findByCodigo(cadastroVenda.getCliente().getCodigo());
		
		//Se Cliente não consta no cadastro
		if(cliente == null) {
			throw new VendaClienteNaoExistenteException();
		}
		
		BigDecimal  total = somandoValoresProdutos(listaProdutos);
		
		List<Fornecedormini> listaFornecedores = listandoFornecedoresmini(listaProdutos);
		
		Venda venda = new Venda(cadastroVenda.getCodigo(), total, cadastroVenda.getDataVenda(), cliente, listaFornecedores, listaProdutos);
		
		return venda;
	}


	private List<Fornecedormini> listandoFornecedoresmini(List<Produto> listaProdutos) {
		
		List<Produto> listaDeProdutos = listaProdutos; 
		List<Fornecedormini> listaFornecedores = new ArrayList<Fornecedormini>();
			
		for (Produto produto : listaDeProdutos) {
			Fornecedormini mini = fmr.findByCodigo(produto.getFornecedor().getCodigo());
			if(mini == null) {
				throw new FornecedorNaoExistenteException();
			}
			listaFornecedores.add(mini);
		}
		return listaFornecedores;
	}


	private BigDecimal somandoValoresProdutos(List<Produto> listaProdutos) {
		
		List<Produto> listaDeProdutos = listaProdutos; 
		
		//Somando os valores dos produtos
		BigDecimal  total = new BigDecimal("0.00");
		
		for (Produto produto : listaDeProdutos) {
		
			if(produto.getPromocao()) {
				total= total.add(produto.getvalorpromo());
				System.out.println("total " + total);
			}else {
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
			
			//Se código produto for NULL ou Sem produto
			if (n.getCodigo() == null) {
				throw new EmptyResultDataAccessException(1);
			}
			
			Produto p = pr.findByCodigo(n.getCodigo());
			
			//Se Produto não consta no cadastro
			if(p == null) {
				throw new ProdutoNaoExistenteException(null);
			}
			lp.add(p);
		}
		return lp;
	}
	
	
	public void validandoFornecedoresEProdutos(CadastroVenda cadastroVenda, List<Produto> listaP) {
		/*
		 * Se Fornecedor indicado na Venda não tem algum dos Produtos da Venda
		 * */
		List <Fornecedormini> mini = cadastroVenda.getFornecedores();
		
		for(Fornecedormini m : mini) {//Passando por cada fornecedor da lista de venda
			Fornecedor f = fr.findByCodigo(m.getCodigo());
			if (f != null) {
				
				for(Produto p: listaP) {//Passando por cada produto da lista de venda
						
					if(!f.getProdutos().contains(p)) {
						throw new FornecedorNaoContemProdutoSelecionadoException(
							"Fornecedor "+ f.getCodigo()+ " não tem o produto: "
							+ p.getCodigo() );}
				}
			}else {//Se fornecedor exite no cadastro.
				throw new FornecedorNaoExistenteException();
			}
			System.out.println("MINI "+ m.getCodigo());
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

