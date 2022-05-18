package com.lordeluan.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.dtos.ClienteDTO;
import com.lordeluan.helpdesk.entity.Cliente;
import com.lordeluan.helpdesk.entity.Pessoa;
import com.lordeluan.helpdesk.repository.ClienteRepository;
import com.lordeluan.helpdesk.repository.PessoaRepository;
import com.lordeluan.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.lordeluan.helpdesk.service.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		 Optional<Cliente> obj = repository.findById(id);
		 return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado id: " + id));
	}
	

	public List<ClienteDTO> findAll() {
		List<Cliente> listCliente = repository.findAll();
		List<ClienteDTO> listDTO = listCliente.stream().map( x-> new ClienteDTO(x)).collect(Collectors.toList());
		return listDTO;
	}

	public ClienteDTO create(ClienteDTO cliDTO) {
		cliDTO.setId(null);
		cliDTO.setSenha(encoder.encode(cliDTO.getSenha()));
		validaPorCpfEEmail(cliDTO);
		return new ClienteDTO(repository.save(new Cliente(cliDTO)));
	}

	public Cliente update(Integer id, @Valid ClienteDTO cliDTO) {
		cliDTO.setId(id);
		Cliente oldTecDto = findById(id);
		validaPorCpfEEmail(cliDTO);
		oldTecDto = new Cliente(cliDTO);
		return repository.save(oldTecDto);
	}
	
	private void validaPorCpfEEmail(ClienteDTO cliDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(cliDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != cliDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(cliDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != cliDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
		}
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
//		Se o cliente tiver pelo menos 1 chamado não poderá ser deletado e será lançada uma exceção
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}
		repository.delete(obj);
	}


}
