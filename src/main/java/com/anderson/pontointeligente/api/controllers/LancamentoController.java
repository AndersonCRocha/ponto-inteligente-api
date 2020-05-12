package com.anderson.pontointeligente.api.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.pontointeligente.api.dtos.LancamentoDTO;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.entities.Lancamento;
import com.anderson.pontointeligente.api.entities.enums.TipoLancamentoEnum;
import com.anderson.pontointeligente.api.services.FuncionarioService;
import com.anderson.pontointeligente.api.services.LancamentoService;
import com.anderson.pontointeligente.api.utils.Response;
import com.anderson.pontointeligente.api.utils.converters.LancamentoConverter;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	
	@Value("${paginacao.page_size}") private int pageSize;
	
	@Autowired
	private LancamentoService lancamentoService;
	@Autowired
	private FuncionarioService funcionarioService;
	
	@PostMapping
	public ResponseEntity<Response<LancamentoDTO>> cadastrar(@Valid @RequestBody LancamentoDTO lancamentoDTO, 
			BindingResult result) throws ParseException {
		
		log.info("Cadastrando lançamento: {}", lancamentoDTO);
		Response<LancamentoDTO> response = new Response<>();
		
		validarDadosLancamento(lancamentoDTO, result, false);
		Lancamento lancamento = LancamentoConverter.convertDTOparaEntity(lancamentoDTO);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados do lancamento: ", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		lancamento = lancamentoService.save(lancamento);
		response.setData(LancamentoConverter.convertEntityParaDTO(lancamento));
		
		return new ResponseEntity<Response<LancamentoDTO>>(response, HttpStatus.CREATED);
	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<LancamentoDTO>> atualizar(@PathVariable("id")Long id,
			@Valid @RequestBody LancamentoDTO lancamentoDTO, BindingResult result) throws ParseException{
		
		log.info("Atualizando lancamento: {}", lancamentoDTO);
		Response<LancamentoDTO> response = new Response<>();
		
		lancamentoDTO.setId(Optional.of(id));
		validarDadosLancamento(lancamentoDTO, result, true);
		Lancamento lancamento = LancamentoConverter.convertDTOparaEntity(lancamentoDTO);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados do lançamento: {}", lancamentoDTO);
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		lancamento = lancamentoService.save(lancamento);
		response.setData(LancamentoConverter.convertEntityParaDTO(lancamento));
		
		return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Void>> remover(@PathVariable("id")Long id){
		
		log.info("Removendo lançamento com id: {}", id);
		Response<Void> response = new Response<>();
		
		try {
			lancamentoService.delete(id);
		}catch(Exception e) {
			response.getErrors().add("Erro ao tentar remover registro: "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/funcionario/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<LancamentoDTO>>> listarLancamentosPorFuncionario(
			@PathVariable("funcionarioId")Long funcionarioId,
			@RequestParam(value = "page", defaultValue = "0")int page,
			@RequestParam(value = "order", defaultValue = "id")String order,
			@RequestParam(value = "direction", defaultValue = "DESC")String direction){
		
		log.info("Listando lancamentos do funcionario com id: {}, pagina: {}", funcionarioId, page);
		Response<Page<LancamentoDTO>> response = new Response<>();
		
		PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), order);
		Page<Lancamento> lancamentos = lancamentoService.findByFuncionarioId(funcionarioId, pageRequest);
		Page<LancamentoDTO> lancamentosDTO = lancamentos.map(LancamentoConverter::convertEntityParaDTO);
		
		response.setData(lancamentosDTO);
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<LancamentoDTO>> perquisarLancamento(@PathVariable("id")Long id){
		
		log.info("Buscando lancamento com id: {}", id);
		Response<LancamentoDTO> response = new Response<>();
		
		Optional<Lancamento> lancamentoOpt = lancamentoService.findById(id);
		if(!lancamentoOpt.isPresent()) {
			log.info("Lançamento não encontrado para o id: {}", id);
			response.getErrors().add("Lançamento não encontrado para o id "+id);
			return ResponseEntity.badRequest().body(response);
		}
		LancamentoDTO lancamentoDTO = LancamentoConverter.convertEntityParaDTO(lancamentoOpt.get());
		response.setData(lancamentoDTO);

		return ResponseEntity.ok(response);
		
	}
	
	private void validarDadosLancamento(LancamentoDTO dto, BindingResult result, boolean isAtualizar) {

		if(isAtualizar) {
			Optional<Lancamento> lancamentoOpt = lancamentoService.findById(dto.getId().get());
			if(!lancamentoOpt.isPresent()) {
				result.addError(new ObjectError("lancamento", "Nenhum lançamento encontrado para o id informado: " + dto.getId().get()));
			}
		}
		
		if(dto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Nenhum funcionário informado"));
			return;
		}
		
		Optional<Funcionario> funcionarioOpt = funcionarioService.findById(dto.getFuncionarioId());
		if(!funcionarioOpt.isPresent()) {
			result.addError(new ObjectError("funcionario", "Nenhum funcionario encontrado com o id informado"));
		}
		
		if(!EnumUtils.isValidEnum(TipoLancamentoEnum.class, dto.getTipo())) {
			result.addError(new ObjectError("lancamento","Tipo inválido de lançamento: "+ dto.getTipo()));
		}
		
	}
	
}
