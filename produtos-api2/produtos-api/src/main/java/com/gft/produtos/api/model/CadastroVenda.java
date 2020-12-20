package com.gft.produtos.api.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class CadastroVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Cliente cliente;
	
	@NotNull
	private List<ProdutoListagem> produtos;
	
	@NotNull
	private Fornecedormini fornecedor;

	@NotNull
	//@ApiModelProperty(example="2020-05-12")
	@ApiModelProperty(example="12/05/2020")
	@Column(name = "data_compra")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private LocalDate dataCompra;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ProdutoListagem> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoListagem> produtos) {
		this.produtos = produtos;
	}

	public Fornecedormini getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedormini fornecedor) {
		this.fornecedor = fornecedor;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) throws ParseException {
		this.dataCompra = dataCompra;
	}
		
		
	
}
