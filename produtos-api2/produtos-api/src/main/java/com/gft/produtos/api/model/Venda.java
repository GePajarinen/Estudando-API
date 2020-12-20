package com.gft.produtos.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "venda")
@ApiModel("Venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(example="3")
	private Long id;
	
	@ApiModelProperty(example="21.90")
	@Column(name = "total_compra")
	private BigDecimal totalCompra;
	
	@NotNull
	@Column(name = "data_compra")
	@ApiModelProperty(example="2020-05-12")
	//@ApiModelProperty(example="03/12/2020")
	//@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCompra;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente")
	private Cliente cliente;
	
	@NotNull
	@OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "venda_produtos",
            joinColumns = @JoinColumn(name = "venda_id"),
            inverseJoinColumns = @JoinColumn(name = "produtos_id"))
	private List<Produto> produtos;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "venda_fornecedor",
            joinColumns = @JoinColumn(name = "venda_id"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id"))
	private Fornecedormini fornecedor;
	
			
	public Venda() {}
	
	public Venda(Long id, BigDecimal totalCompra, LocalDate dataCompra, @NotNull Cliente cliente, Fornecedormini fornecedor,
			@NotNull List<Produto> produtos) {
		//super();
		this.totalCompra = totalCompra;
		this.dataCompra = dataCompra;
		this.cliente = cliente;
		this.fornecedor = fornecedor;
		this.produtos = produtos;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Fornecedormini getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedormini fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
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
		Venda other = (Venda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
