package com.anderson.pontointeligente.api.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	static final String CLAIM_KEY_USERNAME = "sub"; 
	static final String CLAIM_KEY_ROLE = "role"; 
	static final String CLAIM_KEY_CREATED = "created";
	
	@Value("${jwt.secret}") 
	private String secret;
	@Value("${jwt.expiration}") 
	private Long expiration;
	
	/**
	 * Obtém o username(email) contido no token	
	 * 
	 * @param token
	 * @return String
	 */
	public String getUsernameFromToken(String token) {
		
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch(Exception e) {
			e.printStackTrace();
			username = null;
		}
		
		return username;
		
	}
	
	/**
	 * Retorna a data de expiração do token
	 * 
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			e.printStackTrace();
			expiration = null;
		}
		
		return expiration;
	
	}
	
	/**
	 * Gera um novo token
	 * 
	 * @param token
	 * @return String
	 */
	public String refreshToken(String token) {
		
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = gerarToken(claims);
		} catch (Exception e) {
			e.printStackTrace();
			refreshedToken = null;
		}
		
		return refreshedToken;
		
	}
	
	/**
	 * Gera um token
	 * 
	 * @param claims
	 * @return
	 */
	private String gerarToken(Map<String, Object> claims) {
		
		return Jwts.builder().setClaims(claims)
				.setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	 
	}
	
	/**
	 * verifica se um token é válido
	 * 
	 * @param token
	 * @return
	 */
	public boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}
	
	/**
	 * Verifica se o token está expirado
	 * 
	 * @param token
	 * @return
	 */
	private boolean tokenExpirado(String token) {
		
		Date dataExpiracao = getExpirationDateFromToken(token);
		
		return dataExpiracao != null ? dataExpiracao.before(new Date()) : false;
		
	}
	
	/**
	* Retorna a data de expiração com base na data atual.
	*
	* @return Date
	*/
	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	/**
	 * Realiza um parse no token pra obter as informações contidas nele
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) {
		
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			claims = null;
		}
		
		return claims;
		
	}
	
	/**
	 * Gera um novo token conforme os dados do usuário
	 * 
	 * @param userDetails
	 * @return
	 */
	public String obterToken(UserDetails userDetails) {
		
		Map <String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities()
			.forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		
		return gerarToken(claims);
		
	}
	
}
