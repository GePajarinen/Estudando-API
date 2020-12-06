package com.gft.produtos.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gft.produtos.api.service.exception.FornecedorNaoExistenteException;
import com.gft.produtos.api.service.exception.FornecedorVazioException;
import com.gft.produtos.api.service.exception.ListaDeProdutosVaziaException;
import com.gft.produtos.api.service.exception.ProdutoNaoExistenteException;

@ControllerAdvice
public class ProdutosExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource ms;
	


	@Override
	//Se adicionar atributos que não existem no original
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request){
		
		String mensagemUsuario = ms.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() !=null ? ex.getCause().toString() : ex.toString();
		
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
		
	}
	
	
	@Override
	//Se passar um argumento que não é válido
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request){
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
		
	}
	
	//Quando tenta deletar OU atualizar o que não existe.
		@ExceptionHandler({EmptyResultDataAccessException.class})
		public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
			String mensagemUsuario = ms.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = ex.toString(); 
			
			List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
			
		}
	
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String mensagemUsuario = ms.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
			
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
			
		}
	
	//Se não colocar nada na busca pelo nome
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
	                                 WebRequest request) {
	  
		String mensagemUsuario = ms.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
			
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	
	}
	
	//Exception especial pra PRODUTO. CASO Cadastrar VENDA sem Produto no cadastro
		@ExceptionHandler({ProdutoNaoExistenteException.class})

		public ResponseEntity<Object> handleProdutoNaoExistenteException(ProdutoNaoExistenteException ex, WebRequest request) {
			String mensagemUsuario = ms.getMessage("produto.inexistente", null, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = ex.toString(); 
			
			List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
			
		}
	
		
	
			
	//CASO Cliente não exista no cadastro
	@ExceptionHandler({ListaDeProdutosVaziaException.class})
	public ResponseEntity<Object> handleListaDeProdutosVaziaException(ListaDeProdutosVaziaException ex) {
		String mensagemUsuario = ms.getMessage("listaprodutos.vazia", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	
	//CADASTRO PRODUTO SEM FORNECEDOR
		@ExceptionHandler({FornecedorVazioException.class})
		public ResponseEntity<Object> handleFornecedorVazioException(FornecedorVazioException ex) {
			String mensagemUsuario = ms.getMessage("fornecedor.vazio", null, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = ex.toString();
			
			List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return ResponseEntity.badRequest().body(erros);
		}
				
	//CASO FORNECEDOR não exista no cadastro
	@ExceptionHandler({FornecedorNaoExistenteException.class})
	public ResponseEntity<Object> handleFornecedorNaoExistenteException(FornecedorNaoExistenteException ex) {
		String mensagemUsuario = ms.getMessage("fornecedor.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
		
		
		
		
		
	//Criando a lista de erros e "bindind" os tipos de erros dentro da lista.
		private List<Erro> criarListaDeErros(BindingResult bindingResult){
			List<Erro> erros = new ArrayList<>();
			
			for(FieldError fieldError : bindingResult.getFieldErrors()) {
				String mensagemUsuario = ms.getMessage(fieldError, LocaleContextHolder.getLocale());
				String mensagemDesenvolvedor = fieldError.toString();
				erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			}
			
			return erros;
		}
		

		
		//Classe de Erros para usuário e desenvolvedor
		public static class Erro {
			
			private String mensagemUsuario;
			private String mensagemDesenvolvedor;
			
			public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
				super();
				this.mensagemUsuario = mensagemUsuario;
				this.mensagemDesenvolvedor = mensagemDesenvolvedor;
			}

			public String getMensagemUsuario() {
				return mensagemUsuario;
			}

			public String getMensagemDesenvolvedor() {
				return mensagemDesenvolvedor;
			}
			
		}
			
	
	
}
