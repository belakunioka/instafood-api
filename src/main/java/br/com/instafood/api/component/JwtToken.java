package br.com.instafood.api.component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken implements Serializable {

	private static final long serialVersionUID = -8282603117550319625L;

	@Value("${jwt.token.secret}")
	private String secret;
	
	@Value("${jwt.token.expiration.login}")
	public long loginExpiration;
	
	@Value("${jwt.token.expiration.reset}")
	public long resetExpiration;
	
	@Value("${jwt.token.expiration.confirm}")
	public long confirmExpiration;
	
	private String generate(String email, long expiration) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public String generateLoginToken(String username) {
		return generate(username, loginExpiration);
	}
	
	public String generateResetToken(String username) {
		return generate(username, resetExpiration);
	}
	
	public String generateConfirmationToken(String username) {
		return generate(username, confirmExpiration);
	}
	
	public String getEmail(String token) {
		return getClaims(token).getSubject();
	}
	
	public Boolean isTokenExpired(String token) {
		final Date expiration = getClaims(token).getExpiration();
		return expiration.before(new Date());
	}
}
