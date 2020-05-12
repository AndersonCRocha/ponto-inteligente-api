package com.anderson.pontointeligente.api.services;

import java.util.Optional;

import com.anderson.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {
	
	/**
	 * Busca um funcionario pelo cpf
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findByCpf(String cpf);

	/**
	 * Busca um funcionario pelo email
	 * 
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findByEmail(String email);
	
	/**
	 * Busca um funcionario pelo id
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findById(Long id);

	/**
	 * Busca um funcionário pelo cpf ou email
	 * 
	 * @param cpf
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findByCpfOrEmail(String cpf, String email);
	
	/**
	 * Salva um funcionario na base de dados
	 * 
	 * @param funcionario
	 * @return Funcionario
	 */
	Funcionario save(Funcionario funcionario);
	
	/**
	 * Atualiza os dados de um funcionário
	 * 
	 * @param funcionario
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> update(Funcionario funcionario);	
	
}
