package com.anderson.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.repositories.EmpresaRepository;
import com.anderson.pontointeligente.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	public static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> findByCnpj(String cnpj) {

		log.info("Buscando na base a empresa com o cnpj: {}", cnpj);

		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));

	}

	@Override
	public Empresa save(Empresa empresa) {

		log.info("Salvando empresa: {}", empresa);
		
		return empresaRepository.save(empresa);
		
	}

}
