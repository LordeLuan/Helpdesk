package com.lordeluan.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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
		tecDTO.setSenha(encoder.encode(tecDTO.getSenha()));
		return new TecnicoDTO(repository.save(new Tecnico(tecDTO)));
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO tecDTO) {
		tecDTO.setId(id);
		Tecnico oldTecDto = findById(id);
		
		if(!tecDTO.getSenha().equals(oldTecDto.getSenha())) {
			tecDTO.setSenha(encoder.encode(tecDTO.getSenha()));
		}
		
		validaPorCpfEEmail(tecDTO);
		oldTecDto = new Tecnico(tecDTO);
		return repository.save(oldTecDto);
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

	public void delete(Integer id) {
		Tecnico obj = findById(id);
//		Se o ténico tiver pelo menos 1 chamado não poderá ser deletado e será lançada uma exceção
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}
		repository.delete(obj);
	}


}
