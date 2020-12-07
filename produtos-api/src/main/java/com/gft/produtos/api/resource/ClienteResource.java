package com.gft.produtos.api.resource;

import java.time.LocalDate;
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
import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.repository.ClienteRepository;
import com.gft.produtos.api.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

		
	@Autowired
	private ClienteRepository cr;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@Autowired
	private ClienteService cs;

	
	
	//LISTAR CLIENTES
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de clientes")
	@GetMapping
	public List<Cliente> listarClientes(){
		return cr.findAll();
	}
	
		
	//INSERIR CLIENTES
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Cadastrar cliente")
	@PostMapping
	public ResponseEntity<Cliente> cadastrarCliente(
			@ApiParam(name = "Corpo", value = "Representação de um novo cliente")
			@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		
		LocalDate data = LocalDate.now();
		cliente.setdatacadastro(data);
		
		Cliente clienteSalvo = cr.save(cliente);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
			
		}
		
		
	//BUSCAR CLIENTE PELO ID
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Busca de cliente pelo código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscaPeloCodigo(
			@ApiParam(value="Código do cliente", example = "5")
			@PathVariable Long codigo) {
		
		Cliente cliente = cr.findById(codigo).orElse(null);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	
	//ATUALIZAR CLIENTE
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Atualizar cadastro de cliente")
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizarCliente(
			@ApiParam(value="Código do cliente", example = "5")
			@PathVariable Long codigo, 
			
			@ApiParam(name = "Corpo", value = "Representação do cliente com os dados atualizados")
			@Valid @RequestBody Cliente cliente){
			
		Cliente clienteAtualizado = cs.atualizar(codigo, cliente);
			
		return ResponseEntity.ok(clienteAtualizado);
	}
	

	//EXCLUIR CLIENTE
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Excluir cliente do cadastro")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCliente(
			@ApiParam(value="Código do cliente", example = "10")
			@PathVariable Long codigo) {
			
		cr.deleteById(codigo);
	}
	
		
		
	//LISTAR CLIENTES ORDEM ALFA CRESC
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de clientes em ordem alfabética crescente")
	@GetMapping("/asc")
	public List<Cliente> ordernarAsc(){
		List<Cliente> asc = cr.findAllByOrderByNomeAsc();
		return asc;
	}
	
	
	//LISTAR CLIENTES ORDEM ALFA DRECR
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de clientes em ordem alfabética decrescente")
	@GetMapping("/desc")
	public List<Cliente> ordernarDesc(){
		List<Cliente> desc = cr.findAllByOrderByNomeDesc();
		return desc;
	}
		
		
	//BUSCAR CLIENTE POR NOME
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar cliente pelo nome")
	@GetMapping("/nome/{nome}")
	public @ResponseBody List<Cliente> procuraPorNome(
			@ApiParam(value="Nome da pessoa", example = "Julian") 
			@PathVariable Optional<String> nome){
		
		if(nome.isPresent()) {
			return cr.findByNomeContaining(nome.get());
		}else{
			return cr.findAll(); //Não funciona //tentar return ResponseEntity.notFound().build();
		}
	}
	
	
	
	
}
