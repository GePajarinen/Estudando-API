package com.gft.produtos.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProdutosExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource ms;
	


	@Override
	//Se adicionar parâmetros que não existem no original
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request){
		
		String mensagemUsuario = ms.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() !=null ? ex.getCause().toString() : ex.toString();
		//String mensagemDesenvolvedor = ex.getCause().toString();
		
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
