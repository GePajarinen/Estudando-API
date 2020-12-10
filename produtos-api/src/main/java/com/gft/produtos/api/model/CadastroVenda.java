package com.gft.produtos.api.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CadastroVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	private Cliente cliente;
	
	@NotNull
	private List<ProdutoListagem> produtos;
	
	@NotNull
	private Fornecedormini fornecedor;

	@NotNull
	@ApiModelProperty(example="2020-12-05")
	private LocalDate dataVenda;
	
	
	
	
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Fornecedormini getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedormini fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	


}
