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
import com.gft.produtos.api.model.Fornecedormini;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.model.ProdutoListagem;
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.ClienteRepository;
import com.gft.produtos.api.repository.FornecedorRepository;
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
			throw new EmptyResultDataAccessException(1);
		}
		
		Cliente cliente = cr.findByCodigo(cadastroVenda.getCliente().getCodigo());
		
		//Se Cliente não consta no cadastro
		if(cliente == null) {
			throw new VendaClienteNaoExistenteException();
		}
		
		List<Produto> listaDeProdutos = listaProdutos; 
		List<Fornecedormini> listaFornecedores = new ArrayList<Fornecedormini>();
		
		
		BigDecimal  total = new BigDecimal("0.00");
		
		for (Produto produto : listaDeProdutos) {
			
		
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
		
		return venda;
	}



	public List<Produto> criarListaProdutos(@Valid CadastroVenda cadastroVenda) {
		
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
				throw new ProdutoNaoExistenteException();
			}
			
			lp.add(p);
		}
		
		return lp;
	}
	
	
	
	
	public void validandoFornecedoresEProdutos(CadastroVenda cadastroVenda, List<Produto> listaP) {

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


	//Evitando alterações no Cliente// Mas se trocar o Código, muda o cliente
	public void tratandoCliente(Venda venda) {
		
		Cliente c = cr.findByCodigo(venda.getCliente().getCodigo());
		
		//Se cleinte nao consta: null ou não existe
		if (c== null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		venda.setCliente(c);
		
	}




	
	
	
	
	
}

