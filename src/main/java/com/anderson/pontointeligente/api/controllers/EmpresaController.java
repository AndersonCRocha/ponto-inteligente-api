package com.anderson.pontointeligente.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.pontointeligente.api.dtos.EmpresaDTO;
import com.anderson.pontointeligente.api.entities.Empresa;
import com.anderson.pontointeligente.api.services.EmpresaService;
import com.anderson.pontointeligente.api.utils.Response;
import com.anderson.pontointeligente.api.utils.converters.EmpresaConverter;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
	
	public static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDTO>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		
		Response<EmpresaDTO> response = new Response<>();
		
		Optional<Empresa> empresaOpt = empresaService.findByCnpj(cnpj);
		if(!empresaOpt.isPresent()) {
			log.info("Empresa não encontrada para o CNPJ: {}", cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ: "+cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(EmpresaConverter.convertEntityParaEmpresaDTO(empresaOpt.get()));
		return ResponseEntity.ok(response);
		
	}
	
}
