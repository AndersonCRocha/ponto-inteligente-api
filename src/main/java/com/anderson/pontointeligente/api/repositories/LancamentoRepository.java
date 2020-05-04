package com.anderson.pontointeligente.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.anderson.pontointeligente.api.entities.Lancamento;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
				query = "SELECT lan FROM Lancamento lan WHERE lan.funcionario.id = :funcionarioId")
})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId")Long funcionarioId);
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId")Long funcionarioId, Pageable pageable);
	
}
