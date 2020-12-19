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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Produto")
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
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de produtos")
	@GetMapping
	public List<Produto> listarProdutos(){
		return pr.findAll();
	}

		
	//INSERIR PRODUTOS
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Cadastrar produto")
	@PostMapping
	public ResponseEntity<Produto> cadastrarProduto(
			@ApiParam(name = "Corpo", value = "Representação de um novo produto")
			@Valid @RequestBody Produto produto, HttpServletResponse response) {
				
		ps.addProdutoEmFornecedor(produto); //Adicionar produto na lista do Fornecedor
		
		ps.addFornecedorMiniDoProduto(produto);//Relaciona o Fornecedormini ao produto
		
		Produto produtoSalvo = pr.save(produto);
			
		pub.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
			
		}
		
		
	//BUSCAR PRODUTO PELO ID
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar produto pelo código")
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscaPeloId(
			@ApiParam(value="Id do produto", example = "4")
			@PathVariable Long id) {
		
		Produto produto = pr.findById(id).orElse(null);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	
	//ATUALIZAR PRODUTO
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Atualizar cadastro do produto")
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizarProduto(
			@ApiParam(value="Id do produto", example = "4")
			@PathVariable Long id,
			
			@ApiParam(value="Id do produto", example = "4")
			@Valid @RequestBody Produto produto){
		
		ps.verificarFornecedor(produto); //Tratando se Fornecedor for NULL ou errado
		
		Produto produtoAtualizado = ps.atualizar(id, produto);
		return ResponseEntity.ok(produtoAtualizado);
			
	}
	

	//EXCLUIR PRODUTO
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Excluir produto do cadastro")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerProduto(
			
			@ApiParam(value="Id do produto", example = "4")
			@PathVariable Long id) {
			
		ps.retirarDaListaDoFornecedor(id); //Retira Produto da lista do Fornecedor
			
		pr.deleteById(id);
	}
	
		
	//LISTAR PRODUTOS ORDEM ALFA CRESC
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Listar produtos pelo nome em ordem alfabética crescente")
	@GetMapping("/asc")
	public List<Produto> ordernarAsc(){
		List<Produto> asc = pr.findAllByOrderByNomeAsc();
		return asc;
	}
	
	
	//LISTAR PRODUTOS ORDEM ALFA DRECR
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Listar produtos pelo nome em ordem alfabética decrescente")
	@GetMapping("/desc")
	public List<Produto> ordernarDesc(){
		List<Produto> desc = pr.findAllByOrderByNomeDesc();
		return desc;
	}
		
		
	//BUSCAR PRODUTO POR NOME
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar produto pelo nome")
	@GetMapping("/nome/{nome}")
	public @ResponseBody List<Produto> procuraPorNome(
			@ApiParam(value="Nome do produto", example = "Caderno")
			@PathVariable Optional<String> nome){
		
		if(nome.isPresent()) {
			return pr.findByNomeContaining(nome.get());
		}else{
			return pr.findAll(); //pra não ficar sem return List. Mas vou ver um erro pra isso
		}
	}
	
	
	//ATUALIZAR PRODUTO EM PROMOÇÃO
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Atualizar o status de promoção do produto")
	@PutMapping("/{id}/promocao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPromocao(
			@ApiParam(value="Id do produto", example = "8")
			@PathVariable Long id, 
			
			@ApiParam(name = "Corpo", value = "Representação do produto com a promoção atualizada")
			@RequestBody Boolean promocao) {
		
		ps.atualizarPromocao(id, promocao);
	}
	
	
}


