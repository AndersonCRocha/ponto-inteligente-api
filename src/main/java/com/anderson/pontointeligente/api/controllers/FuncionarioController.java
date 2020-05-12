package com.anderson.pontointeligente.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.pontointeligente.api.dtos.FuncionarioDTO;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.services.FuncionarioService;
import com.anderson.pontointeligente.api.utils.PasswordUtils;
import com.anderson.pontointeligente.api.utils.Response;
import com.anderson.pontointeligente.api.utils.converters.FuncionarioConverter;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<FuncionarioDTO>> atualizar(@PathVariable("id")Long id,
			@Valid @RequestBody FuncionarioDTO funcionarioDTO,
			BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Atualizando funcionario: {}", funcionarioDTO);
		Response<FuncionarioDTO> response = new Response<>();
		Funcionario funcionario = null;
		
		Optional<Funcionario> funcionarioOpt = funcionarioService.findById(id);
		if(!funcionarioOpt.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado"));
		} else {
			funcionario = atualizaDadosFuncionario(funcionarioOpt.get(), funcionarioDTO, result);
		}
		
		if(result.hasErrors()) {
			log.error("Erro validando o funcionario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		funcionarioService.update(funcionario);
		response.setData(FuncionarioConverter.convertEntityParaFuncionarioDTO(funcionario));
		
		return ResponseEntity.ok(response);
	
	}
	
	private Funcionario atualizaDadosFuncionario(Funcionario funcionario, FuncionarioDTO funcionarioDTO, BindingResult result) {
	
		funcionario.setNome(funcionarioDTO.getNome());
		
		if(!funcionario.getEmail().equals(funcionarioDTO.getEmail())) {
			funcionarioService.findByEmail(funcionarioDTO.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já cadastrado")));
			funcionario.setEmail(funcionarioDTO.getEmail());
		}
		
		funcionarioDTO.getValorHora()
			.ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		
		funcionarioDTO.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));

		funcionarioDTO.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		
		funcionarioDTO.getSenha()
			.ifPresent(senha -> funcionario.setSenha(PasswordUtils.gerarBCrypt(senha)));
		
		return funcionario;
	
	}
	
}
