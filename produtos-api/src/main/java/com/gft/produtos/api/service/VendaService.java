package com.gft.produtos.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.CadastroVenda;
import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.model.ProdutoListagem;
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.ClienteRepository;
import com.gft.produtos.api.repository.ProdutoRepository;
import com.gft.produtos.api.repository.VendaRepository;
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

	public Venda atualizar(Long codigo, Venda venda) {
		Venda vendaAtualizada = buscarVendaPeloCodigo(codigo);
		BeanUtils.copyProperties(venda, vendaAtualizada, "codigo");
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
		
		//Se Cliente NULL
		if (cadastroVenda.getCliente().getCodigo() == null) {
			System.out.println("BLABLA");
			throw new EmptyResultDataAccessException(1);
		}
		
		Cliente cliente = cr.findByCodigo(cadastroVenda.getCliente().getCodigo());
		
		//Se Cliente não consta no cadastro
		if(cliente == null) {
			throw new VendaClienteNaoExistenteException();
		}
		
		
		System.out.println("cliente "+ cadastroVenda.getCliente().getCodigo() );
		
		List<Produto> listaDeProdutos = listaProdutos; 
		List<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();
		
		System.out.println("cliente  "+ cliente.getNome() );
		
		BigDecimal  total = new BigDecimal("0.00");
		System.out.println("AQUI  ");
		
		for (Produto produto : listaDeProdutos) {
			
			System.out.println("p CODIGO "+ produto.getCodigo());
		
		
			if(produto.getPromocao()) {
				total= total.add(produto.getvalorpromo());
				System.out.println("total " + total);
			}else {
				total = total.add(produto.getValor());
				System.out.println("total " + total);
			}
			
			listaFornecedores.add(produto.getFornecedor());
		}
		
		
		LocalDate data = LocalDate.now();
		Venda venda = new Venda(cadastroVenda.getCodigo(), total, data, cliente, listaFornecedores, listaDeProdutos);
		
		System.out.println("venda codigo "+ venda.getCodigo());
		
		return venda;
	}



	public List<Produto> criarListaProdutos(@Valid CadastroVenda cadastroVenda) {
		
		List<Produto> lp = new ArrayList<Produto>();
		List<ProdutoListagem> listagem = cadastroVenda.getProdutos();
		
		for(ProdutoListagem n : listagem) {
			
			//Se código produto for NULL ou Sem produto
			if (n.getCodigo() == null) {
				System.out.println("PEGUEI!! ");
				throw new EmptyResultDataAccessException(1);
			}
			
			Produto p = pr.findByCodigo(n.getCodigo());
			
			System.out.println("PRODUTO " + p);
			
			//Se Produto não consta no cadastro
			if(p == null) {
				throw new ProdutoNaoExistenteException();
			}
			
			lp.add(p);
		}
		
		
		System.out.println("lp size "+ lp.size());
		return lp;
	}
	
	
}

