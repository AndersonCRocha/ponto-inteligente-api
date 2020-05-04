package com.anderson.pontointeligente.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.pontointeligente.api.entities.Lancamento;
import com.anderson.pontointeligente.api.repositories.LancamentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@MockBean
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private LancamentoService lancamentoService;
	
	@Before
	public void setUp() throws Exception{
		BDDMockito
			.given(lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
			.willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
		BDDMockito.given(lancamentoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));
		BDDMockito.given(lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
	}
	
	@Test
	public void testFindByFuncionarioId() {
		Page<Lancamento> lancamentos = lancamentoService.findByFuncionarioId(1L, PageRequest.of(0, 10));
		
		assertNotNull(lancamentos);
	}
	
	@Test
	public void testFindById() {
		Optional<Lancamento> lancamentoOpt = lancamentoService.findById(1L);
		
		assertTrue(lancamentoOpt.isPresent());
	}
	
	@Test
	public void testSave() {
		Lancamento lancamento = lancamentoService.save(new Lancamento());
		
		assertNotNull(lancamento);
	}
	
}
