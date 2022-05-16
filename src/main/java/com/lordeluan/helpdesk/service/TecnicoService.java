package com.lordeluan.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.dtos.TecnicoDTO;
import com.lordeluan.helpdesk.entity.Pessoa;
import com.lordeluan.helpdesk.entity.Tecnico;
import com.lordeluan.helpdesk.repository.PessoaRepository;
import com.lordeluan.helpdesk.repository.TecnicoRepository;
import com.lordeluan.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.lordeluan.helpdesk.service.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
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
		validaPorCpfEEmail(tecDTO);
		return new TecnicoDTO(repository.save(new Tecnico(tecDTO)));
	}

	private void validaPorCpfEEmail(TecnicoDTO tecDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(tecDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != tecDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(tecDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != tecDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
		}
	}
}
