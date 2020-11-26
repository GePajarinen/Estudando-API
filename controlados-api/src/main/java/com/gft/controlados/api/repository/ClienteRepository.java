package com.gft.controlados.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.controlados.api.model.Cliente;

public interface ClienteRepository extends JpaRepository <Cliente, Long>  {

}
