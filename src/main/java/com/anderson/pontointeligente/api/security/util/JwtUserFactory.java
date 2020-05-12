package com.anderson.pontointeligente.api.security.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.entities.enums.PerfilEnum;
import com.anderson.pontointeligente.api.security.beans.JwtUser;

public class JwtUserFactory {

	/**
	 * Cria um JwtUser a partir de um funcionário
	 * 
	 * @param funcionario
	 * @return JwtUser
	 */
	public static JwtUser create(Funcionario funcionario) {
		return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(), 
				mapToGrantedAuthorities(funcionario.getPerfil()));
	}

	/**
	 * Converte o perfil do funcionário para o formato suportado pelo Spring
	 * 
	 * @param perfil
	 * @return
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfil){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil.toString()));
		return authorities;
	}
	
}
