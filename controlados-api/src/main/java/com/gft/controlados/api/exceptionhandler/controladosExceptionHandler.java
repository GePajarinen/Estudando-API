package com.gft.controlados.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class controladosExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource ms;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request){
		
		String mensagemUsuario = ms.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		//String mensagemDesenvolvedor = ex.getCause() !=null ? ex.getCause().toString() : ex.toString();
		String mensagemDesenvolvedor = ex.getCause().toString();
		
		List<Erro> erros = Arrays.asList( new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
		
	}
	
	
	
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
		
	
		private List<Erro> criarListaDeErros(BindingResult bindingResult){
			List<Erro> erros = new ArrayList<>();
			
			for(FieldError fieldError : bindingResult.getFieldErrors()) {
				String mensagemUsuario = ms.getMessage(fieldError, LocaleContextHolder.getLocale());
				String mensagemDesenvolvedor = fieldError.toString();
				erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			}
			
			return erros;
		}
			
		
	}
