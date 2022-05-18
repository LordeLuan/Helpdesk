package com.lordeluan.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

//	Pega as informações do campos do application.properties para usar no método
	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.secret}")
	private String secret;

//	Metodo que gera o token
	public String generateToken(String email) {
		return Jwts.builder()
				// quem é o usuario autenticado
				.setSubject(email)
				// data de expiração
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				// token cripritografado
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expiratinDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());

			if (username != null && expiratinDate != null && now.before(expiratinDate)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {

		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
