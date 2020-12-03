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
	@GetMapping
	public List<Cliente> listarClientes(){
		return cr.findAll();
	}
	
		
	//INSERIR CLIENTES
	@PostMapping
	public ResponseEntity<Cliente> cadastrarCliente(
			@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		
		LocalDate data = LocalDate.now();
		cliente.setdatacadastro(data);
		
		Cliente clienteSalvo = cr.save(cliente);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
			
		}
		
		
	//BUSCAR CLIENTE PELO ID
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscaPeloCodigo(
			@PathVariable Long codigo) {
		
		Cliente cliente = cr.findById(codigo).orElse(null);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	
	//ATUALIZAR CLIENTE
		@PutMapping("/{codigo}")
		public ResponseEntity<Cliente> atualizarCliente(
				@PathVariable Long codigo, 
				@Valid @RequestBody Cliente cliente){
			
			Cliente clienteAtualizado = cs.atualizar(codigo, cliente);
			
			return ResponseEntity.ok(clienteAtualizado);
		}
	

	//EXCLUIR CLIENTE
		@DeleteMapping("/{codigo}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void removerCliente(
				@PathVariable Long codigo) {
			
			cr.deleteById(codigo);
		}
	
		
		
	//LISTAR CLIENTES ORDEM ALFA CRESC
	@GetMapping("/asc")
	public List<Cliente> ordernarAsc(){
		List<Cliente> asc = cr.findAllByOrderByNomeAsc();
		return asc;
	}
	
	
	//LISTAR CLIENTES ORDEM ALFA DRECR
	@GetMapping("/desc")
	public List<Cliente> ordernarDesc(){
		List<Cliente> desc = cr.findAllByOrderByNomeDesc();
		return desc;
	}
		
		
	//BUSCAR CLIENTE POR NOME
	@GetMapping("/nome/{nome}")
	public @ResponseBody List<Cliente> procuraPorNome(@PathVariable Optional<String> nome){
		if(nome.isPresent()) {
			return cr.findByNomeContaining(nome.get());
		}else{
			return cr.findAll(); //Não funciona
		}
	}
	
	
	
	
}
