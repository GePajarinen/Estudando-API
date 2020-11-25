package com.gft.money.api.resource;

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

import com.gft.money.api.event.RecursoCriadoEvent;
import com.gft.money.api.model.Pessoa;
import com.gft.money.api.repository.PessoaRepository;
import com.gft.money.api.service.PessoaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Pessoas")
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pr;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@Autowired
	private PessoaService ps;
		
	@ApiOperation("Cadastrar pessoa")
	@PostMapping
	public ResponseEntity<Pessoa> cadastrarPessoa(
			@ApiParam(name = "Corpo", value = "Representação de uma nova pessoa no cadastro")
			@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		
		Pessoa pessoaSalva = pr.save(pessoa);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
		
	}
	
	@ApiOperation("Busca pessoa pelo código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscaPeloCodigo(
			@ApiParam(value="ID da pessoa", example = "1") 
			@PathVariable Long codigo) {
		
		Pessoa pessoa = pr.findById(codigo).orElse(null);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation("Remove pessoa da lista")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoa(
			@ApiParam(value="ID da pessoa", example = "1")
			@PathVariable Long codigo) {
		
		pr.deleteById(codigo);
	}
	
	@ApiOperation("Atualizar dados da pessoa")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizarPessoa(
			@ApiParam(value="ID da pessoa", example = "1")
			@PathVariable Long codigo, 
			
			@ApiParam(name = "Corpo", value = "Representação da pessoa com os dados atualizados")
			@Valid @RequestBody Pessoa pessoa){
		
		Pessoa pessoaAtualizada = ps.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaAtualizada);
		
	}
	
	@ApiOperation("Atualiza o status da pessoa")
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(
			@ApiParam(value="ID da pessoa", example = "1")
			@PathVariable Long codigo, 
			
			@ApiParam(value="Status da pessoa: Ativo (True or False)")
			@RequestBody Boolean ativo) {
		
		ps.atualizarAtivo(codigo, ativo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}