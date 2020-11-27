package com.gft.controlados.api.resource;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.controlados.api.event.RecursoCriadoEvent;
import com.gft.controlados.api.model.Cliente;
import com.gft.controlados.api.repository.ClienteRepository;
import com.gft.controlados.api.service.ClienteService;

@RestController //Rest porque já transforma pra JASON
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteRepository cr;
	
	@Autowired
	private ClienteService cs;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@GetMapping
	public List<Cliente> listarCliente(){
		
		return cr.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<Cliente> cadastrarCliente(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalvo = cr.save(cliente);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscaPeloCodigo(
			//@ApiParam(value="ID da categoria", example = "5")
			@PathVariable Long codigo) {
		Cliente cliente = cr.findById(codigo).orElse(null);
		
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizarCliente(
			//@ApiParam(value="ID da pessoa", example = "11")
			@PathVariable Long codigo, 
			
			//@ApiParam(name = "Corpo", value = "Representação da pessoa com os dados atualizados")
			@Valid @RequestBody Cliente cliente){
		
		Cliente clienteAtualizado = cs.atualizar(codigo, cliente);
		return ResponseEntity.ok(clienteAtualizado);
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCliente(
			//@ApiParam(value="ID da pessoa", example = "11")
			@PathVariable Long codigo) {
		
		cr.deleteById(codigo);
	}
}
