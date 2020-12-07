package com.gft.produtos.api.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class ProdutoListagem {
	
	@NotNull
	private Long codigo;

	
	
	@ApiModelProperty(example="11")
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
}
