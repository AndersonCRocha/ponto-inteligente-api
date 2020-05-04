package com.anderson.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	public static final Logger log = org.slf4j.LoggerFactory.getLogger(PasswordUtils.class);
	
	/**
	 * Gera um hash utilizando o BCrypt
	 * 
	 * @param senha
	 * @return String
	 */
	public static String gerarBCrypt(String senha) {
		if(senha == null) {
			return senha;
		}
		
		log.info("Gerando hash com o BCrypt");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
}
