package com.anderson.pontointeligente.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.anderson.pontointeligente.api.entities.enums.TipoLancamentoEnum;

@Entity
@Table(name = "lancamento")
@SequenceGenerator(name = "sq_lancamento", sequenceName = "sq_lancamento", allocationSize = 1)
public class Lancamento {

	private Long id;
	private Date data;
	private String descricao;
	private String localizacao;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private TipoLancamentoEnum tipo;
	private Funcionario funcionario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sq_lancamento")
	public Long getId() {
		return id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getData() {
		return data;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getLocalizacao() {
		return localizacao;
	}
	
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	@Column(name = "data_atualizacao")
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public TipoLancamentoEnum getTipo() {
		return tipo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	public void setTipo(TipoLancamentoEnum tipo) {
		this.tipo = tipo;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@PrePersist
	public void prePersist() {
		dataCriacao = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		dataCriacao = new Date();
	}
	
	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", data=" + data + ", descricao=" + descricao + ", localizacao=" + localizacao
				+ ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", tipo=" + tipo
				+ ", funcionario=" + funcionario + "]";
	}
	
}
