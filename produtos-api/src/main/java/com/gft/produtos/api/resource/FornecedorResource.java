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
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.repository.FornecedorRepository;
import com.gft.produtos.api.repository.FornecedorminiRepository;
import com.gft.produtos.api.service.FornecedorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Fornecedor")
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

		
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@Autowired
	private FornecedorService fs;
	
	@Autowired
	private FornecedorminiRepository fmr;
	
		
	//LISTAR FORNECEDORES
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de fornecedores")
	@GetMapping
	public List<Fornecedor> listarFornecedores(){
	
		return fr.findAll();
	}
			
	
	//INSERIR FORNECEDORES
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Cadastrar fornecedor")
	@PostMapping
	public ResponseEntity<Fornecedor> cadastrarFornecedor(
			@ApiParam(name = "Corpo", value = "Representação de um novo fornecedor")
			@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
		
		Fornecedor fornecedorSalvo = fr.save(fornecedor);
		fs.salvaFornecedorMini(fornecedor);//Instanciar Fornecedormini
			
		pub.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
			
		}
		
		
	//BUSCAR FORNECEDOR PELO ID
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar fornecedor pelo código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Fornecedor> buscaPeloCodigo(
			@ApiParam(value="Código do fornecedor", example = "3")
			@PathVariable Long codigo) {
		
		Fornecedor fornecedor = fr.findById(codigo).orElse(null);
		return fornecedor != null ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
	}
	
	
	
	//ATUALIZAR FORNECEDOR
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Atualizar cadastro do fornecedores")
	@PutMapping("/{codigo}")
	public ResponseEntity<Fornecedor> atualizarFornecedor(
			@ApiParam(value="Código do fornecedor", example = "3")
			@PathVariable Long codigo, 
			
			@ApiParam(name = "Corpo", value = "Representação do fornecedor atualizado")
			@Valid @RequestBody Fornecedor fornecedor){
		
		fs.manterProdutos(codigo, fornecedor );//Pra não perder a lista de produtos na atualização
			
		Fornecedor fornecedorAtualizado = fs.atualizar(codigo, fornecedor);
		return ResponseEntity.ok(fornecedorAtualizado);
			
	}
	
	
	//EXCLUIR FORNECEDOR
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Excluir fornecedor do cadastro")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFornecedor(
			@ApiParam(value="Código do fornecedor", example = "3")	
			@PathVariable Long codigo) {
		
		fmr.deleteById(codigo); //Deletar taambém o Fornecedormini
			
		fr.deleteById(codigo);
	}
	
		
	//LISTAR FORNECEDORES ORDEM ALFA CRESC
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de fornecedores pelo nome em ordem alfabética crescente")
	@GetMapping("/asc")
	public List<Fornecedor> ordernarAsc(){
		List<Fornecedor> asc = fr.findAllByOrderByNomeAsc();
		return asc;
	}
	
	
	//LISTAR FORNECEDORES ORDEM ALFA DRECR
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de fornecedores pelo nome em ordem alfabética decrescente")
	@GetMapping("/desc")
	public List<Fornecedor> ordernarDesc(){
		List<Fornecedor> desc = fr.findAllByOrderByNomeDesc();
		return desc;
	}
		
		
	//BUSCAR FORNECEDOR POR NOME
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Busca de fornecedor pelo nome")
	@GetMapping("/nome/{nome}")
	public @ResponseBody List<Fornecedor> procuraPorNome(
			@ApiParam(value="Nome do fornecedor", example = "Bayer")
			@PathVariable Optional<String> nome){
		if(nome.isPresent()) {
			return fr.findByNomeContaining(nome.get());
		}else{
			return fr.findAll(); //pra não ficar sem return List. Mas vou ver um erro pra isso
		}
	}
	
	
}	


