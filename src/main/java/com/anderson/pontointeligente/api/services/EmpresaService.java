package com.anderson.pontointeligente.api.services;

import java.util.Optional;

import com.anderson.pontointeligente.api.entities.Empresa;

public interface EmpresaService {
	
	/**
	 * Retorna uma empresa a partir de um CNPJ
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	Optional <Empresa> findByCnpj(String cnpj);
	
	/**
	 * Salva uma empresa na base de dados
	 * 
	 * @param empresa
	 * @return Empresa
	 */
	Empresa save(Empresa empresa);
	
}
