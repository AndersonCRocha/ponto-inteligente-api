package com.anderson.pontointeligente.api.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa {
	
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private Date dataCriacao;
	private Date dataAtualização;
	
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(name = "razao_social", nullable = false)
	public String getRazaoSocial() {
		return razaoSocial;
	}

	@Column(nullable = false)
	public String getCnpj() {
		return cnpj;
	}

	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	@Column(name = "data_atualizacao")
	public Date getDataAtualização() {
		return dataAtualização;
	}

	@OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataAtualização(Date dataAtualização) {
		this.dataAtualização = dataAtualização;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@PreUpdate
	public void preUpdate() {
		dataAtualização = new Date();
	}
	
	@PrePersist
	public void prePersist() {
		dataCriacao = new Date();
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", dataCriacao=" + dataCriacao
				+ ", dataAtualização=" + dataAtualização + ", funcionarios=" + funcionarios + "]";
	}
	
}
