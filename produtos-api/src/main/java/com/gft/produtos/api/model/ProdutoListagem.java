package com.gft.produtos.api.model;

import javax.validation.constraints.NotNull;

public class ProdutoListagem {
	
	@NotNull
	private Long codigo;

	
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
}
