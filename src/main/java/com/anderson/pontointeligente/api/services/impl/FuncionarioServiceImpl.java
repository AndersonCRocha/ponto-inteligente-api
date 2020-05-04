package com.anderson.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.repositories.FuncionarioRepository;
import com.anderson.pontointeligente.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class); 
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public Optional<Funcionario> findByCpf(String cpf) {
		
		log.info("Buscando o funcionário com cpf: {}", cpf);
		return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
	
	}

	@Override
	public Optional<Funcionario> findByEmail(String email) {
		
		log.info("Buscando o funcionário com email: {}", email);
		return Optional.ofNullable(funcionarioRepository.findByEmail(email));
		
	}

	@Override
	public Optional<Funcionario> findById(Long id) {

		log.info("Buscando o funcionário com id: {}", id);
		return funcionarioRepository.findById(id);

	}

	@Override
	public Funcionario save(Funcionario funcionario) {

		log.info("Salvando o funcionário: {}", funcionario);
		return funcionarioRepository.save(funcionario);
		
	}
	
}
