package com.gft.produtos.api.service.exception;

public class ProdutoNaoExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//Não tira! Em ProdutoService tem msg editada
	public ProdutoNaoExistenteException(String msg){
        super(msg);
	}
}