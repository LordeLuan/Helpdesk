package com.lordeluan.helpdesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.entity.Chamado;
import com.lordeluan.helpdesk.repository.ChamadoRepository;
import com.lordeluan.helpdesk.service.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	
	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Chamado não encontrado! ID: " + id));
	}
}
