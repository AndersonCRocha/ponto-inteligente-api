package com.anderson.pontointeligente.api.security.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDTO {

	private String email;
	private String senha;

	@NotEmpty(message = "Email não pode ser vazio.")
	@Email(message = "Email inválido.")
	public String getEmail() {
		return email;
	}

	@NotEmpty(message = "Senha não pode ser vazia.")
	public String getSenha() {
		return senha;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [email=" + email + ", senha=" + senha + "]";
	}
	
}
