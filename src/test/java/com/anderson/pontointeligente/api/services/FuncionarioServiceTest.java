package com.anderson.pontointeligente.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.repositories.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {
	
	@MockBean
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
	}
	
	@Test
	public void TestSave() {
		Funcionario funcionario = funcionarioService.save(new Funcionario());
		
		assertNotNull(funcionario);
	}
	
	@Test
	public void testFindByCpf() {
		Optional<Funcionario> funcionarioOpt = funcionarioService.findByCpf("12345678945");
		
		assertTrue(funcionarioOpt.isPresent());
	}

	@Test
	public void testFindByEmail() {
		Optional<Funcionario> funcionarioOpt = funcionarioService.findByEmail("email@email.com");
		
		assertTrue(funcionarioOpt.isPresent());
	}

	@Test
	public void testFindById() {
		Optional<Funcionario> funcionarioOpt = funcionarioService.findById(1L);
		
		assertTrue(funcionarioOpt.isPresent());
	}

}
