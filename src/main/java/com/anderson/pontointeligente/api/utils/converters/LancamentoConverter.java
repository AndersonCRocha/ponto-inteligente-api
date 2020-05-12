package com.anderson.pontointeligente.api.utils.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;

import com.anderson.pontointeligente.api.dtos.LancamentoDTO;
import com.anderson.pontointeligente.api.entities.Funcionario;
import com.anderson.pontointeligente.api.entities.Lancamento;
import com.anderson.pontointeligente.api.entities.enums.TipoLancamentoEnum;

public class LancamentoConverter {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static LancamentoDTO convertEntityParaDTO(Lancamento lancamento) {
		
		LancamentoDTO dto = new LancamentoDTO();
		dto.setData(dateFormat.format(lancamento.getData()));
		dto.setDescricao(lancamento.getDescricao());
		dto.setFuncionarioId(lancamento.getFuncionario().getId());
		dto.setId(Optional.ofNullable(lancamento.getId()));
		dto.setLocalizacao(lancamento.getLocalizacao());
		dto.setTipo(lancamento.getTipo().toString());
		
		return dto;
	}

	public static Lancamento convertDTOparaEntity(LancamentoDTO lancamentoDTO) throws ParseException {
		
		Lancamento lancamento = new Lancamento();
		if(lancamentoDTO.getId().isPresent()) {
			lancamento.setId(lancamentoDTO.getId().get());
		}
		lancamento.setData(dateFormat.parse(lancamentoDTO.getData()));
		lancamento.setDescricao(lancamentoDTO.getDescricao());
		lancamento.setFuncionario(new Funcionario(lancamentoDTO.getFuncionarioId()));
		lancamento.setLocalizacao(lancamentoDTO.getLocalizacao());
		lancamento.setTipo(EnumUtils.getEnum(TipoLancamentoEnum.class, lancamentoDTO.getTipo()));
		
		return lancamento;
		
	}

}
