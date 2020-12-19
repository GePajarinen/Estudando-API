package com.gft.produtos.api.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class ProdutoListagem {
	
	@NotNull
	private Long id;

	
	@ApiModelProperty(example="11")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
