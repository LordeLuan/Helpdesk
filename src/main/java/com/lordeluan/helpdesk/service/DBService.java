package com.lordeluan.helpdesk.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void intanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Luan Lordello", "12036009093", "luan@gmail.com", encoder.encode("123"));
		Tecnico tec2 = new Tecnico(null, "Alfred Makiavel", "38236317056", "Alfred@gmail.com", encoder.encode("Mordaaaa"));
		Tecnico tec3 = new Tecnico(null, "Mordekai Rigby", "99613635076", "Mordekai@gmail.com", encoder.encode("modaaaal"));
		tec1.addPerfil(Perfil.ADMIN);
		tec2.addPerfil(Perfil.ADMIN);
		tec3.addPerfil(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Linus Torvalds", "80527954780", "Torvalds@gmail.com", encoder.encode("Torvalds"));
		Cliente cli2 = new Cliente(null, "Batman Silva", "44265461093", "Batman@gmail.com", encoder.encode("Silva"));
		Cliente cli3 = new Cliente(null, "Xerox Homicida", "50388199075", "Xerox@gmail.com", encoder.encode("Xerox"));

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "Chamado 02", "Segundo chamado", tec2, cli2);
		Chamado c3 = new Chamado(null, Prioridade.ALTA, Status.ENCERRADO, "Chamado 03", "Terceiro chamado", tec3, cli3);

		tecnicoRepository.saveAll(Arrays.asList(tec1,tec2,tec3));
		clienteRepository.saveAll(Arrays.asList(cli1,cli2,cli3));
		chamadoRepository.saveAll(Arrays.asList(c1,c2,c3));

	}
}
