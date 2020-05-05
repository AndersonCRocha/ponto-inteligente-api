package com.anderson.pontointeligente.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPJDTO {

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String razaoSocial;
	private String cnpj;
	
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
	
	@NotEmpty(message = "A senha não pode ser vazia")
	public String getSenha() {
		return senha;
	}

	@NotEmpty(message = "O CPF não pode ser vazio")
	@CPF(message = "CPF inválido")
	public String getCpf() {
		return cpf;
	}
	
	@NotEmpty(message = "Razão social não pode ser vazia")
	@Length(min = 5, max = 200, message = "A razão social deve ter entre {min} e {max} caracteres.")
	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	@NotEmpty(message = "O CNPJ não pode ser vazio")
	@CNPJ(message = "CNPJ inválido.")
	public String getCnpj() {
		return cnpj;
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
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "PessoaJuridicaDTO [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf="
				+ cpf + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
	
	
	
}
