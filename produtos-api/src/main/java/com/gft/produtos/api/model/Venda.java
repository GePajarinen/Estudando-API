package com.gft.produtos.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	
	private BigDecimal valor;
	
	private LocalDate datacompra;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente")
	private Cliente cliente;
	
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "venda_fornecedor",
            joinColumns = @JoinColumn(name = "venda_codigo"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_codigo"))
	private List<Fornecedor> fornecedores;
	
	
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "venda_produtos",
            joinColumns = @JoinColumn(name = "venda_codigo"),
            inverseJoinColumns = @JoinColumn(name = "produtos_codigo"))
	private List<Produto> produtos;
	
		
	public Venda() {}
	
	public Venda(Long codigo, BigDecimal valor, LocalDate datacompra, @NotNull Cliente cliente, List<Fornecedor> fornecedores,
			@NotNull List<Produto> produtos) {
		//super();
		this.valor = valor;
		this.datacompra = datacompra;
		this.cliente = cliente;
		this.fornecedores = fornecedores;
		this.produtos = produtos;
	}

	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getdatacompra() {
		return datacompra;
	}

	public void setdatacompra(LocalDate datacompra) {
		this.datacompra = datacompra;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
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
		Venda other = (Venda) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	

}
