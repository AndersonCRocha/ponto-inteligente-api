package com.anderson.pontointeligente.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.anderson.pontointeligente.api.entities.Lancamento;
import com.anderson.pontointeligente.api.repositories.LancamentoRepository;
import com.anderson.pontointeligente.api.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	public List<Lancamento> findByFuncionarioId(Long funcionarioId) {

		log.info("Buscando lançamentos para o funcionário: {}", funcionarioId);
		return lancamentoRepository.findByFuncionarioId(funcionarioId);
	
	}

	@Override
	public Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest) {

		log.info("Buscando lançamentos para o funcionário: {}", funcionarioId);
		return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
		
	}

	@Override
	public Optional<Lancamento> findById(Long id) {

		log.info("Buscando lançamento com id: {}", id);
		return lancamentoRepository.findById(id);
	}

	@Override
	public Lancamento save(Lancamento lancamento) {

		log.info("Salvando lancamento: {}", lancamento);
		return lancamentoRepository.save(lancamento);

	}

	@Override
	public void delete(Long id) {

		log.info("Removendo lancamento com id: {}", id);
		lancamentoRepository.deleteById(id);
		
	}

}
