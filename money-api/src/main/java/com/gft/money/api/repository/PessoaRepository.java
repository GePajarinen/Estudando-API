package com.gft.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.money.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
