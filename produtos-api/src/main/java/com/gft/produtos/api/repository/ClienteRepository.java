package com.gft.produtos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

}
