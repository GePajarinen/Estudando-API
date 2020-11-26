package com.gft.controlados.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.controlados.api.model.Medicamento;
import com.gft.controlados.api.repository.MedicamentoRepository;

@Service
public class MedicamentoService {
	
	@Autowired
	private MedicamentoRepository mr;
	
	public Medicamento atualizar(Long codigo, Medicamento medicamento) {
		Medicamento medicamentoAtualizado = buscarMedicamentoPeloCodigo(codigo);
		BeanUtils.copyProperties(medicamento, medicamentoAtualizado, "codigo");
		return mr.save(medicamentoAtualizado);
	}
	
	public Medicamento buscarMedicamentoPeloCodigo(Long codigo) {
		Medicamento medicamentoAtualizado = mr.findById(codigo).orElse(null);
		if (medicamentoAtualizado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return medicamentoAtualizado;
	}

}
