package com.lordeluan.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.dtos.ChamadoDTO;
import com.lordeluan.helpdesk.entity.Chamado;
import com.lordeluan.helpdesk.entity.Cliente;
import com.lordeluan.helpdesk.entity.Tecnico;
import com.lordeluan.helpdesk.entity.enums.Prioridade;
import com.lordeluan.helpdesk.entity.enums.Status;
import com.lordeluan.helpdesk.repository.ChamadoRepository;
import com.lordeluan.helpdesk.service.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	
	@Autowired
	private ChamadoRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Chamado não encontrado! ID: " + id));
	}

	public List<ChamadoDTO> findAll() {
		List<Chamado> listChamado = repository.findAll();
		List<ChamadoDTO> listDTO = listChamado.stream().map( x -> new ChamadoDTO(x)).collect(Collectors.toList());
		return listDTO;
	}

	public Chamado create(@Valid ChamadoDTO chamadoDTO) {
		return repository.save(newChamado(chamadoDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
//		Verifica se o id do chamado é nulo / se ele já existe
		Chamado chamado = new Chamado();
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
//		Seta as informações para o novo chamado entidade ser inserido no banco
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}
	
}
