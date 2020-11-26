package com.gft.controlados.api.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

//@ApiModel(value = "Medicamento", description = "Representa um medicamento")
@Entity
@Table(name = "medicamento")	
public class Medicamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(value = "ID da saida", example = "12")
	private Long codigo;
	
	//@ApiModelProperty(example = "Clonazempam")
	@NotBlank
	private String nome;
	
	//@ApiModelProperty(example = "2 mg")
	@NotBlank
	private String concentracao;
	
	//@ApiModelProperty(example = "Genérico")
	private String classe;
	
	//@ApiModelProperty(example = "12.00")
	@NotNull
	private BigDecimal preco;
	
	
	@ManyToMany
	private List<Saida> saidas;

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

	public String getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(String concentracao) {
		this.concentracao = concentracao;
	}


	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}


	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	
	
	public List<Saida> getSaidas() {
		return saidas;
	}

	public void setSaidas(List<Saida> saidas) {
		this.saidas = saidas;
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
		Medicamento other = (Medicamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
	
}

