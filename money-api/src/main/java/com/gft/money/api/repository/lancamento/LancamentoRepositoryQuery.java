package com.gft.money.api.repository.lancamento;

import java.util.List;

import com.gft.money.api.model.Lancamento;
import com.gft.money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
