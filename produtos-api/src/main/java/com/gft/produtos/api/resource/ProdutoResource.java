package com.gft.produtos.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.produtos.api.event.RecursoCriadoEvent;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.repository.ProdutoRepository;
import com.gft.produtos.api.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@Autowired
	private ProdutoService ps;

	
	
	//LISTAR PRODUTOS
	@GetMapping
	public List<Produto> listarProdutos(){
		return pr.findAll();
	}

		
	//INSERIR PRODUTOS
	@PostMapping
	public ResponseEntity<Produto> cadastrarProduto(
			@Valid @RequestBody Produto produto, HttpServletResponse response) {
		
		Produto produtoSalvo = pr.save(produto);
			
		pub.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
			
		}
		
		
	//BUSCAR PRODUTO PELO ID
	@GetMapping("/{codigo}")
	public ResponseEntity<Produto> buscaPeloCodigo(
			@PathVariable Long codigo) {
		
		Produto produto = pr.findById(codigo).orElse(null);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	
	
	//ATUALIZAR PRODUTO
		@PutMapping("/{codigo}")
		public ResponseEntity<Produto> atualizarProduto(
				@PathVariable Long codigo, 
				@Valid @RequestBody Produto produto){
			
			Produto produtoAtualizado = ps.atualizar(codigo, produto);
			return ResponseEntity.ok(produtoAtualizado);
			
		}
	

	//EXCLUIR PRODUTO
		@DeleteMapping("/{codigo}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void removerProduto(
				@PathVariable Long codigo) {
			
			pr.deleteById(codigo);
		}
	
		
		
	//LISTAR PRODUTOS ORDEM ALFA CRESC
	//LISTAR PRODUTOS ORDEM ALFA DRECR
	
		
		
	//BUSCAR PRODUTO POR NOME
	@GetMapping("/nome/{nome}")
	public @ResponseBody List<Produto> procuraPorNome(@PathVariable Optional<String> nome){
		if(nome.isPresent()) {
			return pr.findByNomeContaining(nome.get());
		}else{
			return pr.findAll();
		}
}
	
	
	
	//ATUALIZAR PRODUTO EM PROMOÇÃO
	@PutMapping("/{codigo}/promocao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPromocao(
			@PathVariable Long codigo, 
			@RequestBody Boolean promocao) {
		
		ps.atualizarPromocao(codigo, promocao);
	}
	
	
	
	
		
	
	}


