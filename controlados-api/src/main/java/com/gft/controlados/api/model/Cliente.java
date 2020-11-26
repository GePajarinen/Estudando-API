package com.gft.controlados.api.model;

import java.util.List;

import javax.persistence.Embedded;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
	import javax.validation.constraints.NotBlank;

	//import io.swagger.annotations.ApiModel;
	//import io.swagger.annotations.ApiModelProperty;
	
//@ApiModel(value = "Cliente", description = "Representa um cliente")
@Entity
@Table(name = "cliente")	
public class Cliente {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		//@ApiModelProperty(value = "ID do cliente", example = "11")
		private Long codigo;

		//@ApiModelProperty(example = "Tuvok")
		@NotBlank
		private String nome;
				
		@Embedded
		private Endereco endereco;

		
		@OneToMany
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

		public Endereco getEndereco() {
			return endereco;
		}

		public void setEndereco(Endereco endereco) {
			this.endereco = endereco;
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
			Cliente other = (Cliente) obj;
			if (codigo == null) {
				if (other.codigo != null)
					return false;
			} else if (!codigo.equals(other.codigo))
				return false;
			return true;
		}
		
	}
		
		

