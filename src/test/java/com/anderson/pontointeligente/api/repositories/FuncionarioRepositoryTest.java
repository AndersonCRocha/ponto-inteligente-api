package com.anderson.pontointeligente.api.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.entities.enums.PerfilEnum;
import com.anderson.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String EMAIL = "teste@email.com";
	private static final String CPF = "01526458962";
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa  = empresaRepository.save(getEmpresa());
		funcionarioRepository.save(getFuncionario(empresa));
	}
	
	@Test
	public void testFindByEmail() {
		Funcionario funcionario = funcionarioRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, funcionario.getEmail());
	}
	
	@Test
	public void testeFindByCpf() {
		Funcionario funcionario = funcionarioRepository.findByCpf(CPF);
		
		assertEquals(CPF, funcionario.getCpf());
	}
	
	@Test
	public void testFindByCpfOrEmail() {
		Funcionario funcionario = funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		
		assertNotNull(funcionario);
	}
	
	@Test
	public void testFindByCpfOrEmailInvalid() {
		Funcionario funcionario = funcionarioRepository.findByCpfOrEmail(CPF, "invalid@email.com");
		
		assertNotNull(funcionario);
	}

	@Test
	public void testFindByCpfInvalidOrEmail() {
		Funcionario funcionario = funcionarioRepository.findByCpfOrEmail("00000000000", EMAIL);
		
		assertNotNull(funcionario);
	}
	
	@After
	public void tearDown() {
		empresaRepository.deleteAll();
	}
	
	private Empresa getEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa teste");
		empresa.setCnpj("12345678945612");
		return empresa;
	}
	
	private Funcionario getFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Funcionário teste");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setEmail(EMAIL);
		funcionario.setCpf(CPF);
		funcionario.setEmpresa(empresa);
		return funcionario;
	}
}
