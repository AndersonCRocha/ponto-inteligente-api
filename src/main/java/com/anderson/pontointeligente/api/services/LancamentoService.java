package com.anderson.pontointeligente.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.anderson.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {
	
	/**
	 * Retorna uma lista de lançamentos de determinado funcionário
 	 * 
	 * @param funcionarioId
	 * @return List<Lancamento>
	 */
	List<Lancamento> findByFuncionarioId(Long funcionarioId);
	
	/**
	 * Retorna uma lista de lançamentos páginada de determinado funcionário
	 * 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return List<Lancamento>
	 */
	Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * Retorna um lançamento por id
	 * 
	 * @param id
	 * @return Lancamento
	 */
	Optional<Lancamento> findById(Long id);
	
	/**
	 * Salva um lançamento na base de dados
	 * 
	 * @param lancamento
	 * @return Lancamento
	 */
	Lancamento save(Lancamento lancamento);
	
	/**
	 * Remove um lançamento da base de dados a partir de um id
	 * 
	 * @param id
	 */
	void delete(Long id);

}
