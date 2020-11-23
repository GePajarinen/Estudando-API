package com.gft.money.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.money.api.model.Lancamento;
import com.gft.money.api.model.Pessoa;
import com.gft.money.api.repository.LancamentoRepository;
import com.gft.money.api.repository.PessoaRepository;
import com.gft.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pr;

	@Autowired
	private LancamentoRepository lr;
	
	public Lancamento savarLancamento(Lancamento lancamento) {
		Pessoa pessoa = pr.findById(lancamento.getPessoa().getCodigo()).orElse(null);
		if(pessoa == null || !pessoa.getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lr.save(lancamento);
	}

}
