package com.gft.produtos.api.service.exception;

public class ProdutoNaoExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoExistenteException(String msg){
        super(msg);
	}
	

}