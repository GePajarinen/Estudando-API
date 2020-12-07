package com.gft.produtos.api.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "produto")
@ApiModel("Produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(example="11")
	private Long codigo;
	
	@NotBlank
	@Size(min=3, max=20)
	@ApiModelProperty(example="Lápis")
	private String nome;
	
	@NotBlank
	@ApiModelProperty(example="12584")
	private String codigoproduto;
	
	@NotNull
	@ApiModelProperty(example="1.00")
	private BigDecimal valor;
	
	@NotNull
	@ApiModelProperty(example="0.66")
	private BigDecimal valorpromo;
	
	@NotNull
	@ApiModelProperty(example="1000")
	private Long quantidade;
	
	@ApiModelProperty(example="lapis.jpg")
	private String imagem;
	
	@NotNull
	@ApiModelProperty(example="false")
	private Boolean promocao;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "fornecedor")
	//@JsonIgnore
	@ApiModelProperty(example="1")
	private Fornecedormini fornecedor;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(example="Papelaria")
	private Categoria categoria;
	
	
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

	public String getcodigoproduto() {
		return codigoproduto;
	}

	public void setcodigoproduto(String codigoproduto) {
		this.codigoproduto = codigoproduto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getvalorpromo() {
		return valorpromo;
	}

	public void setvalorpromo(BigDecimal valorpromo) {
		this.valorpromo = valorpromo;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Boolean getPromocao() {
		return promocao;
	}

	public void setPromocao(Boolean promocao) {
		this.promocao = promocao;
	}

	public Fornecedormini getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedormini fornecedor) {
		this.fornecedor = fornecedor;
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
