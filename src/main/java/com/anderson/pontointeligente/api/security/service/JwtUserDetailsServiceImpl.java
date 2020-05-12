package com.anderson.pontointeligente.api.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.security.util.JwtUserFactory;
import com.anderson.pontointeligente.api.services.FuncionarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Funcionario> funcionarioOpt = funcionarioService.findByEmail(username);
		
		if(funcionarioOpt.isPresent()) {
			return JwtUserFactory.create(funcionarioOpt.get());		
		}
		
		throw new UsernameNotFoundException("Email não encontrado");
	}

}
