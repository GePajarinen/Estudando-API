package com.gft.controlados.api.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

//@ApiModel(value = "Saída", description = "Representa uma saída")
@Entity
@Table(name = "saida")
public class Saida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@ApiModelProperty(value = "ID da saida", example = "12")
	private Long codigo;
	

	@NotNull
	@Column(name = "data_saida")
	//@ApiModelProperty(value = "Data da saida", example = "2020-04-22")
	private LocalDate dataSaida;


	@NotNull
	@ManyToMany
	@JoinColumn(name = "codigo_medicamento")
	//@ApiModelProperty(example = "5")
	private List <Medicamento> medicamentos;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_cliente")
	//@ApiModelProperty(example = "1")
	private Cliente cliente;

	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

		
	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
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
		Saida other = (Saida) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

