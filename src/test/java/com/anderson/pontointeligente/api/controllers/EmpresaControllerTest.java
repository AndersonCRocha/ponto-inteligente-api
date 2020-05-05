package com.anderson.pontointeligente.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.services.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {
	
	private static final String URL = "/api/empresas/cnpj/";
	private static final String CNPJ = "80816291000120";
	private static final Long ID = 16L;
	private static final String RAZAO_SOCIAL = "Empresa do Anderson";
	
	@Autowired
	private MockMvc mvc;
	@MockBean
	private EmpresaService empresaService;
	
	@Test
	public void findByCnpjInvalid() throws Exception {
		BDDMockito.given(empresaService.findByCnpj(Mockito.anyString())).willReturn(Optional.empty());
		
		mvc.perform(MockMvcRequestBuilders.get(URL + CNPJ).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ: "+ CNPJ));
	}
	
	@Test 
	public void findByCnpjValid() throws Exception {
		BDDMockito.given(empresaService.findByCnpj(Mockito.anyString()))
			.willReturn(Optional.of(getEmpresa()));
		
		mvc.perform(MockMvcRequestBuilders.get(URL + CNPJ)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.razaoSocial", equalTo(RAZAO_SOCIAL)))
				.andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	private Empresa getEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setId(ID);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		empresa.setCnpj(CNPJ);
		
		return empresa;
	}
}
