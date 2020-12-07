package com.gft.produtos.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Fornecedormini {
	
	@Id
	@NotNull
	@ApiModelProperty(example="1")
	private Long codigo;
	
	@ApiModelProperty(example="GFT")
	private String nome;
	
	@ApiModelProperty(example="07.174.743/0001-27")
	private String cnpj;
	
	public Fornecedormini() {}

	public Fornecedormini(@NotNull Long codigo, String nome, String cnpj) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cnpj = cnpj;
	}
	
	

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	
	
}
