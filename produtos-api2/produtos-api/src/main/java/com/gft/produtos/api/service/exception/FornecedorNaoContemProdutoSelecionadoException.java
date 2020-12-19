package com.gft.produtos.api.service.exception;

public class FornecedorNaoContemProdutoSelecionadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//Em VendaService tem msg editada
	public FornecedorNaoContemProdutoSelecionadoException(String msg){
        super(msg);
	}
	

}