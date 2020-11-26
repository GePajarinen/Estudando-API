package com.gft.controlados.api.model;

import javax.persistence.Embeddable;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModelProperty;

//@Api(tags = "Endereço")
@Embeddable
public class Endereco {

	//@ApiModelProperty(example = "Rua Rebeca")
	private String logradouro;
	
	//@ApiModelProperty(example = "52")
	private String numero;
	
	//@ApiModelProperty(example = "apto 102")
	private String complemento;
	
	//@ApiModelProperty(example = "Bethânia")
	private String bairro;
	
	//@ApiModelProperty(example = "35164-119")
	private String cep;
	
	//@ApiModelProperty(example = "Ipatinga")
	private String cidade;
	
	//@ApiModelProperty(example = "MG")
	private String estado;
	
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
	
