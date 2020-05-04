package com.anderson.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.pontointeligente.api.entities.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "51463645000100";
	
	@Before
	public void setUp() throws Exception {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa teste");
		empresa.setCnpj(CNPJ);

		empresaRepository.save(empresa);
	}

	@Test
	public void testFindByCnpj() {
		Empresa empresa = empresaRepository.findByCnpj(CNPJ);
		System.out.println(empresa);
		assertEquals(CNPJ, empresa.getCnpj());
	}
	
	@After
	public void tearDown() {
		empresaRepository.deleteAll();
	}
	
}
