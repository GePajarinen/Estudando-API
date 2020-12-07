package com.gft.produtos.api.service.exception;

public class FornecedorNaoContemProdutoSelecionadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//private Fornecedor f;
	//private List<Produto> listaProdutos;
	
	
	
	
	public FornecedorNaoContemProdutoSelecionadoException(String msg, Throwable cause){
        super(msg, cause);
	}
	
	/*public FornecedorNaoContemProdutoSelecionadoException(Fornecedor f, List<Produto> listaProdutos) {
		super();
		this.f = f;
		this.listaProdutos = listaProdutos;
		
		System.out.println("O Fornecedor "+ f.getNome() + " não tem algum desses produtos: ");
		for(Produto p: listaProdutos)System.out.println(p.getCodigo());
	}*/
	
	
	/*
	
	public Fornecedor getF() {
		return f;
	}
	public void setF(Fornecedor f) {
		this.f = f;
	}
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}
	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}*/
	
	
	
	
	

}