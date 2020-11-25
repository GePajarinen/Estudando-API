package com.gft.money.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.money.api.event.RecursoCriadoEvent;
import com.gft.money.api.exceptionhandler.MoneyExceptionHandler.Erro;
import com.gft.money.api.model.Lancamento;
import com.gft.money.api.repository.LancamentoRepository;
import com.gft.money.api.repository.filter.LancamentoFilter;
import com.gft.money.api.service.LancamentoService;
import com.gft.money.api.service.exception.PessoaInexistenteOuInativaException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Lançamentos")
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lr;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@Autowired
	private LancamentoService ls;
	
	@Autowired
	private MessageSource ms;
	
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Pesquisa de lançamentos")
	@GetMapping
	public Page<Lancamento> pesquisar(
			LancamentoFilter lancamentoFilter, 
			Pageable pageable) {
		return lr.filtrar(lancamentoFilter, pageable);
	}
	
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar lançamento pelo ID")
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(
			@ApiParam(value="ID do lançamento", example = "5")
			@PathVariable Long codigo) {
		Lancamento lancamento = lr.findById(codigo).orElse(null);

		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Adicionar novo lançamento")
	@PostMapping
	public ResponseEntity<Lancamento> cadastrarLancamento(
			@ApiParam(name = "Corpo", value = "Representação de um novo lançamento")
			@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		
		Lancamento lancamentoSalva = ls.savarLancamento(lancamento);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
		
	}
	
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = ms.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Deletar lançamento pelo ID")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerLancamento(
			@ApiParam(value="ID do lançamento", example = "5")
			@PathVariable Long codigo) {
		
		lr.deleteById(codigo);
	}
	
	
}