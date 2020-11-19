package com.gft.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.money.api.model.Pessoa;
import com.gft.money.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pr;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaAtualizada = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaAtualizada, "codigo");
		return pr.save(pessoaAtualizada);
	}

	public void atualizarAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaAtualizada = pr.findById(codigo).orElse(null);
		pessoaAtualizada.setAtivo(ativo);
		pr.save(pessoaAtualizada);
	}
	
	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaAtualizada = pr.findById(codigo).orElse(null);
		if (pessoaAtualizada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaAtualizada;
	}
}
