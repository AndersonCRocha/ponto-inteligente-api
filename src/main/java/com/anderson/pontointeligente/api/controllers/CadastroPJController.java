package com.anderson.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.pontointeligente.api.dtos.CadastroPJDTO;
import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.services.EmpresaService;
import com.anderson.pontointeligente.api.services.FuncionarioService;
import com.anderson.pontointeligente.api.utils.Response;
import com.anderson.pontointeligente.api.utils.converters.EmpresaConverter;
import com.anderson.pontointeligente.api.utils.converters.FuncionarioConverter;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private EmpresaService empresaService;
	
	@PostMapping
	public ResponseEntity<Response<CadastroPJDTO>> cadastrar(@Valid @RequestBody CadastroPJDTO cadastroPJDTO, 
			BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Cadastrando PJ: {}", cadastroPJDTO);
		Response<CadastroPJDTO> response = new Response<CadastroPJDTO>();

		validarDadosExistentes(cadastroPJDTO, result);
		Empresa empresa = EmpresaConverter.convertDTOparaEntity(cadastroPJDTO);
		Funcionario funcionario = FuncionarioConverter.convertDTOPJparaEntity(cadastroPJDTO);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados de cadastro da PJ: {}", cadastroPJDTO);
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		empresaService.save(empresa);
		funcionario.setEmpresa(empresa);
		funcionarioService.save(funcionario);
		
		response.setData(FuncionarioConverter.convertEntityParaDTOPJ(funcionario));
		
		return new ResponseEntity<Response<CadastroPJDTO>>(response, HttpStatus.CREATED);
	}

	/**
	 * Verifica se a empresa e o funcionário já estão cadastrados no banco de dados
	 * 
	 * @param cadastroPJDTO
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPJDTO cadastroPJDTO, BindingResult result){
		empresaService.findByCnpj(cadastroPJDTO.getCnpj())
			.ifPresent(empresa -> result.addError(new ObjectError("Empresa", "Empresa já cadastrada")));
		funcionarioService.findByCpf(cadastroPJDTO.getCpf())
			.ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "CPF já cadastrado")));
		funcionarioService.findByEmail(cadastroPJDTO.getEmail())
			.ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "Email já cadastrado")));
	}
	
}
