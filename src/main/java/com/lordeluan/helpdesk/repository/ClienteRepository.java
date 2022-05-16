package com.lordeluan.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lordeluan.helpdesk.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
