package com.gft.money.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.money.api.model.Lancamento;
import com.gft.money.api.repository.LancamentoRepository;


@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lr;
	
	@GetMapping
	public List<Lancamento> listar() {
		return lr.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = lr.findById(codigo).orElse(null);

		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
}