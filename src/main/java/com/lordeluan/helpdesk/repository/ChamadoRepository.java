package com.lordeluan.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lordeluan.helpdesk.entity.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
