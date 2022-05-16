package com.lordeluan.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.dtos.TecnicoDTO;
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

	public List<TecnicoDTO> findAll() {
		List<Tecnico> listTecnico = repository.findAll();
		List<TecnicoDTO> listDTO = listTecnico.stream().map( x-> new TecnicoDTO(x)).collect(Collectors.toList());
		return listDTO;
	}

	public TecnicoDTO create(TecnicoDTO tecDTO) {
		return new TecnicoDTO(repository.save(new Tecnico(tecDTO)));
	}
}
