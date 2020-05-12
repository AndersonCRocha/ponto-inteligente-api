package com.anderson.pontointeligente.api.dtos;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class FuncionarioDTO {

	private Long id;
	private String nome;
	private String email;
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "O nome deve ter entre {min} e {max} caracteres.")
	public String getNome() {
		return nome;
	}

	@NotEmpty(message = "Email não pode ser vazio")
	@Length(min = 5, max = 200, message = "O email deve ter entre {min} e {max} caracteres.")
	@Email(message = "Email inválido")
	public String getEmail() {
		return email;
	}

	public Optional<String> getSenha() {
		return senha;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}

	public Optional<String> getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}

	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}

	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}

	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	@Override
	public String toString() {
		return "FuncionarioDTO [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", valorHora="
				+ valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco=" + qtdHorasAlmoco
				+ "]";
	}
	
}
