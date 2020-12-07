package com.gft.produtos.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.gft.produtos.api.exceptionhandler.ProdutosExceptionHandler.Erro;
import com.gft.produtos.api.model.CadastroVenda;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.VendaRepository;
import com.gft.produtos.api.service.VendaService;
import com.gft.produtos.api.service.exception.VendaClienteNaoExistenteException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Venda")
@RestController
@RequestMapping("/api/vendas")
public class VendaResource {

		
	@Autowired
	private VendaRepository vr;
		
	@Autowired
	private ApplicationEventPublisher pub;
	
	@Autowired
	private VendaService vs;

	@Autowired
	private MessageSource ms;

				
		
	//LISTAR VENDAS
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Lista de vendas")
	@GetMapping
	public List<Venda> listarVendas(){
		return vr.findAll();
	}
		
	
	//INSERIR VENDAS
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Cadastrar nova venda")
	@PostMapping
	public ResponseEntity<Venda> cadastrarVenda(
			@ApiParam(name = "Corpo", value = "Representação de uma nova venda")
			@Valid @RequestBody CadastroVenda cadastroVenda, HttpServletResponse response) {
		
		List<Produto> listaP = vs.criarListaProdutos(cadastroVenda);
		
		vs.validandoFornecedores(cadastroVenda, listaP );
		
		
		Venda vendaSalva = vs.criarVenda(cadastroVenda, listaP);
		
		vr.save(vendaSalva);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
				
			
	}
		
		
	//BUSCAR VENDA PELO ID
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar venda pelo código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Venda> buscaPeloCodigo(
			@ApiParam(value="Código da venda", example = "2")
			@PathVariable Long codigo) {
		
		Venda venda = vr.findById(codigo).orElse(null);
		return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
	}
	
	
	
	//ATUALIZAR VENDA
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Atualizar dados da venda")
	@PutMapping("/{codigo}")
	public ResponseEntity<Venda> atualizarVenda(
			@ApiParam(value="Código da venda", example = "2")
			@PathVariable Long codigo, 
			
			@ApiParam(name = "Corpo", value = "Representação de uma venda atualizada")
			@Valid @RequestBody Venda venda){
		
		vs.tratandoCliente(venda);
		
		Venda vendaAtualizada = vs.atualizar(codigo, venda);
		return ResponseEntity.ok(vendaAtualizada);
			
	}
	
	
	//EXCLUIR VENDA
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Excluir venda")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerVenda(
			@ApiParam(value="Código da venda", example = "2")
			@PathVariable Long codigo) {
		
		vr.deleteById(codigo);
	}
	
		
		
	//LISTAR VENDAS PELO NOME DO CLIENTE ORDEM CRESC
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Listar vendas pelo no do cliente em ordem alfabética crescente")
	@GetMapping("/asc")
	public List<Venda> ordernarAsc(){
		
		List<Venda> asc = vr.findAll(Sort.by("cliente.nome"));
		return asc;
	}
	
	
	//LISTAR VENDAS PELO NOME DO CLIENTE ORDEM DESC
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Listar vendas pelo no do cliente em ordem alfabética decrescente")
	@GetMapping("/desc")
	public List<Venda> ordernarDesc(){
		List<Venda> desc = vr.findAll(Sort.by("cliente.nome").descending());
		return desc;
	}
		
	//TROCAR PELO ATRIBUTO CODIGOPRODUTO!!!!!!!!!!!!!!!!!!!!	
	//BUSCAR VENDA PELO CODIGO
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Buscar venda pelo no do cliente em ordem alfabética crescente")
	@GetMapping("/codigo/{codigo}")
	public @ResponseBody List<Venda> procuraPorCodigo(
			@ApiParam(value="Código da venda", example = "2")
			@PathVariable Optional<Long> codigo){
		
		if(codigo.isPresent()) {
			return vr.findByCodigo(codigo.get());
		}else{
			return vr.findAll(); //Não funciona
		}
	}
	
	
	//BUSCAR VENDA PELO NOME CLIENTE
	@ApiImplicitParam(name = "Authorization", 
			value = "Bearer Token", 
			required = true, 
			allowEmptyValue = false, 
			paramType = "header", 
			example = "Bearer access_token")
	@ApiOperation("Listar vendas pelo no do cliente em ordem alfabética crescente")
	@GetMapping("/nome/{nome}")
	public @ResponseBody List<Venda> procuraPorNomeCliente(
			@ApiParam(value="Nome do cliente", example = "Odo") 
			@PathVariable Optional<String> nome){
		
		if(nome.isPresent()) {
			return vs.procurandoPeloNomeCliente(nome.get());
		}
		else{
			return vr.findAll(); //Não funciona
		}
	}
	
		
	//Exception especial pra essa classe. CASO Cliente não exista no cadastro
	@ExceptionHandler({VendaClienteNaoExistenteException.class})
	public ResponseEntity<Object> handleVendaClienteNaoExistenteException(VendaClienteNaoExistenteException ex) {
		String mensagemUsuario = ms.getMessage("cliente.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

}
