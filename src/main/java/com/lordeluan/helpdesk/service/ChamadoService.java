package com.lordeluan.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.dtos.ChamadoDTO;
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

	public List<ChamadoDTO> findAll() {
		List<Chamado> listChamado = repository.findAll();
		List<ChamadoDTO> listDTO = listChamado.stream().map( x -> new ChamadoDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
	
	
}
