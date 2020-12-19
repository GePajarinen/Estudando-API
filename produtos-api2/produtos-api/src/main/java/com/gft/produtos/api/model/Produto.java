package com.gft.produtos.api.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Long id;
	
	@NotBlank
	@Size(min=3, max=20)
	@ApiModelProperty(example="Lápis")
	private String nome;
	
	
	@ApiModelProperty(example="12584")
	@Column(name = "codigo_produto")
	private String codigoProduto;
	
	@NotNull
	@ApiModelProperty(example="1.00")
	private BigDecimal valor;
	
	@NotNull
	@Column(name = "valor_promo")
	@ApiModelProperty(example="0.66")
	private BigDecimal valorPromo;
	
	@NotNull
	@ApiModelProperty(example="1000")
	private Long quantidade;
	
	@ApiModelProperty(example="lapis.jpg")
	private String imagem;
	
	@NotNull
	@ApiModelProperty(example="false")
	private Boolean promocao;
	
	@NotNull
	@ApiModelProperty(example="1")
	@ManyToOne
	private Fornecedormini fornecedor;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(example="Papelaria")
	private Categoria categoria;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorPromo() {
		return valorPromo;
	}

	public void setValorPromo(BigDecimal valorPromo) {
		this.valorPromo = valorPromo;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
