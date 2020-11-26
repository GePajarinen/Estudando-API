package com.gft.controlados.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.controlados.api.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

}
