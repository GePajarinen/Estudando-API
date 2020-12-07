package com.gft.produtos.api.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "cliente")
@ApiModel("Cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(example="11")
	private Long codigo;
	
	@NotBlank
	@Size(min=10, max=100)
	@ApiModelProperty(example="Aline Mafra")
	private String nome;
	
	@NotBlank
	@ApiModelProperty(example="aline.mafra@hotmail.com")
	private String email;
	
	@NotBlank
	@Size(min=3, max=20)
	@ApiModelProperty(example="Mafra1235")
	private String senha;
	
	@NotBlank
	@Size(min=8, max=14) 
	@ApiModelProperty(example="236.323.326-89")
	private String documento;
	
	@ApiModelProperty(example="2020-12-05")
	private LocalDate datacadastro;

	
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public LocalDate getdatacadastro() {
		return datacadastro;
	}

	public void setdatacadastro(LocalDate datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
}
