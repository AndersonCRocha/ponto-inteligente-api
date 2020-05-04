package com.anderson.pontointeligente.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.entities.Lancamento;
import com.anderson.pontointeligente.api.entities.enums.PerfilEnum;
import com.anderson.pontointeligente.api.entities.enums.TipoLancamentoEnum;
import com.anderson.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long funcionarioId;
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa = empresaRepository.save(getEmpresa());
		Funcionario funcionario = funcionarioRepository.save(getFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		
		lancamentoRepository.save(getLancamento(funcionario));
		lancamentoRepository.save(getLancamento(funcionario));
	}
	
	@Test
	public void testFindByFuncionarioId() {
		List<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(this.funcionarioId);
		
		assertEquals(2, lancamentos.size());
	}

	@Test
	public void testFindByFuncionarioIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		Page<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(this.funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
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
		funcionario.setEmail("teste@email.com");
		funcionario.setCpf("12365484956");
		funcionario.setEmpresa(empresa);
		return funcionario;
	}
	
	private Lancamento getLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoLancamentoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}
}
