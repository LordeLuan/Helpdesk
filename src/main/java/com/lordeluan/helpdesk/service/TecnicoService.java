package com.lordeluan.helpdesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.entity.Tecnico;
import com.lordeluan.helpdesk.repository.TecnicoRepository;
import com.lordeluan.helpdesk.service.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		 Optional<Tecnico> obj = repository.findById(id);
		 return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado id: " + id));
	}
}
