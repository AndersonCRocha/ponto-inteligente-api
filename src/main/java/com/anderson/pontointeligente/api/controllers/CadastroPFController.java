package com.anderson.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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

import com.anderson.pontointeligente.api.dtos.CadastroPFDTO;
import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.services.EmpresaService;
import com.anderson.pontointeligente.api.services.FuncionarioService;
import com.anderson.pontointeligente.api.utils.Response;
import com.anderson.pontointeligente.api.utils.converters.FuncionarioConverter;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {
		
	public static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private FuncionarioService funcionarioService;
	
	@PostMapping
	public ResponseEntity<Response<CadastroPFDTO>> cadastrar(@Valid @RequestBody CadastroPFDTO cadastroPFDTO,
			BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Cadastrando PF: {}", cadastroPFDTO);
		Response<CadastroPFDTO> response = new Response<CadastroPFDTO>();
		
		validarDadosExistentes(cadastroPFDTO, result);
		Funcionario funcionario = FuncionarioConverter.convertDTOPFparaEntity(cadastroPFDTO);
		
		Optional<Empresa> empresaOpt = empresaService.findByCnpj(cadastroPFDTO.getCnpj());
		if(!empresaOpt.isPresent()) {
			result.addError(new ObjectError("Empresa", "Empresa não cadastrada"));
		}
		
		if(result.hasErrors()) {
			log.error("Erro validando dados de cadastro da PF: {}", cadastroPFDTO);
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		funcionario.setEmpresa(empresaOpt.get());
		funcionario = funcionarioService.save(funcionario);
		
		response.setData(FuncionarioConverter.convertEntityParaDTOPF(funcionario));
		return new ResponseEntity<Response<CadastroPFDTO>>(response, HttpStatus.CREATED);
	
	}
	
	/**
	 * Valida se os dados do funcionário já estão cadastrados na base de dados
	 * 
	 * @param cadastroPFDTO
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPFDTO cadastroPFDTO, BindingResult result) {
		
		funcionarioService.findByCpf(cadastroPFDTO.getCpf())
			.ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "CPF já cadastrado")));
		funcionarioService.findByEmail(cadastroPFDTO.getEmail())
			.ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "Email já cadastrado")));
		
	}
	
}
