package com.anderson.pontointeligente.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class LancamentoDTO {

	private Optional<Long> id = Optional.empty();
	private String data;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;

	public Optional<Long> getId() {
		return id;
	}

	@NotEmpty(message = "Data não pode ser vazia")
	public String getData() {
		return data;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	@Override
	public String toString() {
		return "LancamentoDTO [id=" + id + ", data=" + data + ", tipo=" + tipo + ", descricao=" + descricao
				+ ", localizacao=" + localizacao + ", funcionarioId=" + funcionarioId + "]";
	}
	
}
