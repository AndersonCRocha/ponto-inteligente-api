package com.anderson.pontointeligente.api.utils.converters;

import com.anderson.pontointeligente.api.dtos.CadastroPJDTO;
import com.anderson.pontointeligente.api.dtos.EmpresaDTO;
import com.anderson.pontointeligente.api.entities.Empresa;

public class EmpresaConverter {

	public static final Empresa convertDTOparaEntity(CadastroPJDTO cadastroPJDTO) {
		return new Empresa(cadastroPJDTO.getCnpj(), cadastroPJDTO.getRazaoSocial());
	}

	public static EmpresaDTO convertEntityParaEmpresaDTO(Empresa empresa) {
		EmpresaDTO empresaDTO = new EmpresaDTO();
		empresaDTO.setId(empresa.getId());
		empresaDTO.setRazaoSocial(empresa.getRazaoSocial());
		empresaDTO.setCnpj(empresa.getCnpj());
		
		return empresaDTO;
	}
	
}
