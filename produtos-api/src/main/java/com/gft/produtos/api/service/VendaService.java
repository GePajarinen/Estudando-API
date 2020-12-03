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
		
		for (Produto produto : listaDeProdutos) {
			System.out.println("p  "+ produto.getCodigo());
			
			
			System.out.println("p  "+ produto.getPromocao() );
			System.out.println("p  "+ produto.getValor() );
			System.out.println("p  "+ produto.getvalorpromo() );
			
			if(produto.getPromocao()) {
				total= total.add(produto.getvalorpromo());
				System.out.println("total " + total);
			}else {
				total = total.add(produto.getValor());
				System.out.println("total " + total);
			}
			
			listaFornecedores.add(produto.getFornecedor());
			System.out.println("for name "+ produto.getFornecedor().getNome());
		}
		
		System.out.println("decimalTOTAL "+ total );
		System.out.println("decimalTOTAL class  "+ total.getClass() );
		
		LocalDate data = LocalDate.now();
		
		Venda venda = new Venda(cadastroVenda.getCodigo(), total, data, cliente, listaFornecedores, listaDeProdutos);
		
		//Eu coloquei um Construtor na classe VENDA pra melhorar isso, mas deu erro não hora de GET vendas
		// ERRO org.hibernate.InstantiationException: No default constructor for entity:
		
		
		/*
		Venda venda = new Venda();
		venda.setCodigo(cadastroVenda.getCodigo());
		venda.setCliente(cliente);
		venda.setValor(total);
		venda.setdatacompra(data);
		venda.setFornecedores(listaFornecedores);
		venda.setProdutos(listaDeProdutos);*/
		
		//vr.save(venda);
		//Com esse, salvar venda nao funciona
		
		
		System.out.println("venda codigo "+ venda.getCodigo());
		
		System.out.println("antes ");
		
		return venda;
	}



	public List<Produto> criarListaProdutos(@Valid CadastroVenda cadastroVenda) {
		
		List<Produto> lp = new ArrayList<Produto>();
		
		List<ProdutoListagem> listagem = cadastroVenda.getProdutos();
		
		for(ProdutoListagem n : listagem) {
			Produto p = pr.findByCodigo(n.getCodigo());
			lp.add(p);
		}
		
		
		System.out.println("lp size "+ lp.size());
		return lp;
	}
	
	
}

