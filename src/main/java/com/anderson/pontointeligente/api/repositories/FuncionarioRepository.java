package com.anderson.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.anderson.pontointeligente.api.entities.Funcionario;

@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Funcionario findByCpf(String cpf);
	Funcionario findByEmail(String email);
	Funcionario findByCpfOrEmail(String cpf, String email);

}
