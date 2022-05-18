package com.lordeluan.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lordeluan.helpdesk.dtos.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

//	Tratitva para tentativa de autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
// 		Pega os valores passado no endpoint /login atrves do request e envia para o obj DTO no metodo abaixo
		try {
			CredenciaisDTO cres = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
//			Passa as informações que pegamos do front para o objeto de autenticação
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(cres.getEmail(),
					cres.getSenha(), new ArrayList<>());
//			intancia e chama um metodo para realizar a autenticação das informações
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

//	Autenticação deu sucesso
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
//		authResult = resultado da autenticação feita anteriormente;
//		Pega o email/principal do resultado da autencação e salva na variavel
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
//		Gera um token baseado no Username recebido da authResult
		String token = jwtUtil.generateToken(username);
//		Resposta da requisição pelo cabeçalho, para posteriormente conseguir recuperar o token
		response.setHeader("access-control-expose-headers", "Authorization");
		response.setHeader("Authorization", "Bearer " + token);
		
	}

//	Autenticação falhou
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	
	}

	private CharSequence json() {
		long date = new Date().getTime();
		return "{"
				+ "\"timestamp\": " + date + ", " 
				+ "\"status\": 401, "
				+ "\"error\": \"Não autorizado\", "
				+ "\"message\": \"Email ou senha inválidos\", "
				+ "\"path\": \"/login\"}";
	}
	

}
