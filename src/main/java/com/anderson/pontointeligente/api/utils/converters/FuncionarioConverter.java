package com.anderson.pontointeligente.api.utils.converters;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.anderson.pontointeligente.api.dtos.CadastroPFDTO;
import com.anderson.pontointeligente.api.dtos.CadastroPJDTO;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.entities.enums.PerfilEnum;
import com.anderson.pontointeligente.api.utils.PasswordUtils;

public class FuncionarioConverter {
	
	public static final Funcionario convertDTOPJparaEntity(CadastroPJDTO cadastroPJDTO) 
			throws NoSuchAlgorithmException {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPJDTO.getCpf());
		funcionario.setNome(cadastroPJDTO.getNome());
		funcionario.setEmail(cadastroPJDTO.getEmail());
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDTO.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		
		return funcionario;
	}
	
	public static final CadastroPJDTO convertEntityParaDTOPJ(Funcionario funcionario) {
		
		CadastroPJDTO cadastroPJDTO = new CadastroPJDTO();
		cadastroPJDTO.setId(funcionario.getId());
		cadastroPJDTO.setNome(funcionario.getNome());
		cadastroPJDTO.setEmail(funcionario.getEmail());
		cadastroPJDTO.setCpf(funcionario.getCpf());
		cadastroPJDTO.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDTO.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPJDTO;
	}

	public static Funcionario convertDTOPFparaEntity(CadastroPFDTO cadastroPFDTO) 
			throws NoSuchAlgorithmException {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPFDTO.getCpf());
		funcionario.setNome(cadastroPFDTO.getNome());
		funcionario.setEmail(cadastroPFDTO.getEmail());
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDTO.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		cadastroPFDTO.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		cadastroPFDTO.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));
		cadastroPFDTO.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		
		return funcionario;
	}
	
	public static final CadastroPFDTO convertEntityParaDTOPF(Funcionario funcionario) {
		
		CadastroPFDTO cadastroPFDTO = new CadastroPFDTO();
		cadastroPFDTO.setId(funcionario.getId());
		cadastroPFDTO.setNome(funcionario.getNome());
		cadastroPFDTO.setEmail(funcionario.getEmail());
		cadastroPFDTO.setCpf(funcionario.getCpf());
		cadastroPFDTO.setCnpj(funcionario.getEmpresa().getCnpj());
		funcionario.getValorHoraOpt().ifPresent(valorHora -> cadastroPFDTO.setValorHora(Optional.of(valorHora+"")));
		funcionario.getQtdHorasTrabalhoDiaOpt()
			.ifPresent(qtdHorasTrabalhoDia -> cadastroPFDTO.setQtdHorasTrabalhoDia(Optional.of(qtdHorasTrabalhoDia+"")));
		funcionario.getQtdHorasAlmocoOpt()
			.ifPresent(qtdHorasAlmoco -> cadastroPFDTO.setQtdHorasAlmoco(Optional.of(qtdHorasAlmoco+"")));
		
		return cadastroPFDTO;
	}
}
