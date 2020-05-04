package com.anderson.pontointeligente.api.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.anderson.pontointeligente.api.entities.enums.PerfilEnum;

@Entity
@Table(name = "funcionario")
public class Funcionario {

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private BigDecimal valorHora;
	private Float qtdHorasTrabalhoDia;
	private Float qtdHorasAlmoco;
	private PerfilEnum perfil;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private Empresa empresa;
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	public String getNome() {
		return nome;
	}

	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	@Column(nullable = false)
	public String getSenha() {
		return senha;
	}

	@Column(nullable = false)
	public String getCpf() {
		return cpf;
	}

	@Column(name = "valor_hora")
	public BigDecimal getValorHora() {
		return valorHora;
	}

	@Column(name = "qtd_horas_trabalho_dia")
	public Float getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}

	@Column(name = "qtd_horas_almoco")
	public Float getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public PerfilEnum getPerfil() {
		return perfil;
	}

	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	@Column(name = "data_atualizacao")
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Lancamento> getLancamentos() {
		return lancamentos;
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

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	@PrePersist
	public void prePersist() {
		dataCriacao = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}
	
	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", perfil=" + perfil + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + ", empresa=" + empresa + ", lancamentos=" + lancamentos + "]";
	}
	
}
