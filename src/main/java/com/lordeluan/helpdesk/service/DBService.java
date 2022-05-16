package com.lordeluan.helpdesk.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordeluan.helpdesk.entity.Chamado;
import com.lordeluan.helpdesk.entity.Cliente;
import com.lordeluan.helpdesk.entity.Tecnico;
import com.lordeluan.helpdesk.entity.enums.Perfil;
import com.lordeluan.helpdesk.entity.enums.Prioridade;
import com.lordeluan.helpdesk.entity.enums.Status;
import com.lordeluan.helpdesk.repository.ChamadoRepository;
import com.lordeluan.helpdesk.repository.ClienteRepository;
import com.lordeluan.helpdesk.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	public void intanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Luan Lordello", "63653230268", "luan@gmail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Linus Torvalds", "80527954780", "Torvalds@gmail.com", "123");

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1,
				cli1);

		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));

	}
}
