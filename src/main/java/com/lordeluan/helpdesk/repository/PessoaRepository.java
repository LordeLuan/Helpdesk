package com.lordeluan.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lordeluan.helpdesk.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
