package com.gft.produtos.api.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
	
	
	public void idClienteNull(Long id) {
		if(id == null) {
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
		
		//Se Cliente.id for NULL
		idClienteNull(cadastroVenda.getCliente().getId());
		
		Cliente cliente = cr.findById(cadastroVenda.getCliente().getId()).orElse(null);
		
		//Se Cliente não consta no cadastro
		clienteNoCadastro(cliente);
		
		BigDecimal  total = somandoValoresProdutos(listaProdutos);
		
		Fornecedormini fornecedor = fmr.findById(cadastroVenda.getFornecedor().getId()).orElse(null);
		
		//Convertendo o Formato da DATA:
		//setDataFormato(cadastroVenda.getDataCompra());
		
		Venda venda = new Venda(cadastroVenda.getId(), total, cadastroVenda.getDataCompra(), cliente, fornecedor, listaProdutos);
		
		return venda;
	}
	
	

	public Venda atualizar(Long id, CadastroVenda cadastroVenda) {
		Venda vendaAtualizada = buscarVendaPeloId(id);
		
		//Se Cliente.id for NULL
		idClienteNull(cadastroVenda.getCliente().getId());
		
		Cliente c = cr.findById(cadastroVenda.getCliente().getId()).orElse(null);
		
		//Se Cliente não consta no cadastro
		clienteNoCadastro(c);
		
		vendaAtualizada.setCliente(c);
		
		vendaAtualizada.setDataCompra(cadastroVenda.getDataCompra());
		
		List<Produto> newlistP = criarListaProdutos(cadastroVenda);
		vendaAtualizada.setProdutos(newlistP);
		
		//Verificando fornecedor
		temFornecedormini(cadastroVenda.getFornecedor());
		
		Fornecedormini f = fmr.findById(cadastroVenda.getFornecedor().getId()).orElse(null);
		vendaAtualizada.setFornecedor(f);
		
		vendaAtualizada.setTotalCompra(somandoValoresProdutos(newlistP));
		
		return vr.save(vendaAtualizada);
	}

		
	public Venda buscarVendaPeloId(Long id) {
		Venda vendaAtualizada = vr.findById(id).orElse(null);
		
		if (vendaAtualizada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendaAtualizada;
	}

	
	private void temFornecedormini(Fornecedormini fmini) {
				
			if(fmini.getId() == null) {
				throw new FornecedorVazioException();
			}
			
			Fornecedormini f = fmr.findById(fmini.getId()).orElse(null);
			
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
				total= total.add(produto.getValorPromo());
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
			
			//Se id produto for NULL
			if (n.getId() == null) {
				throw new CodigoProdutoNaoIndicadoExecption();
			}
			
			Produto p = pr.findById(n.getId()).orElse(null);
			
			//Se Produto não consta no cadastro
			if(p == null) {
				throw new ProdutoNaoExistenteException("O Produto de código " +
						n.getId() + " não existe no cadastro.");
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
		
		Fornecedor f = fr.findById(cadastroVenda.getFornecedor().getId()).orElse(null);
		if (f != null) {
			for(Produto p: listaP) {//Passando por cada produto da lista de venda
					
				if(!f.getProdutos().contains(p)) {
					throw new FornecedorNaoContemProdutoSelecionadoException(
						"Fornecedor "+ f.getId()+ " não tem o produto: "
					+ p.getId() );}
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

