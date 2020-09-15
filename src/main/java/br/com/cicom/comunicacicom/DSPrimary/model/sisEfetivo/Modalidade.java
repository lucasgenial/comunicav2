package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_MODALIDADE")
@SuppressWarnings("serial")
public class Modalidade implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@Basic
	@Column(name = "NOME", unique = true)
	private String nome;

	@OneToOne(targetEntity = Caracteristica.class, fetch=FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "CARACTERISTICA")
	private Caracteristica caracteristica;

	@Basic
	@Column(name = "QTD_SERVIDORES")
	private Integer qtdServidores;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, targetEntity = Recurso.class)
	@JoinTable(name = "TBL_RECURSOS_MODALIDADE", joinColumns = {@JoinColumn(name = "MODALIDADE_FK") }, inverseJoinColumns = { @JoinColumn(name = "RECURSOS_FK") })
	private Set<Recurso> recursos = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Instituicao.class, mappedBy = "modalidades")
	private Set<Instituicao> instituicoes = new HashSet<>();
	
	@Column(name = "COR_GRAFICO")
	private String cor;

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;

	/*
	 * CONSTRUTORES
	 */
	public Modalidade() {
		this.ativo = true;
	}

	public Modalidade(Long id, @NotNull String nome, Caracteristica caracteristica, Integer qtdServidores,
			Set<Recurso> recursos, Set<Instituicao> instituicoes, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.caracteristica = caracteristica;
		this.qtdServidores = qtdServidores;
		this.recursos = recursos;
		this.instituicoes = instituicoes;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public Integer getQtdServidores() {
		return qtdServidores;
	}

	public void setQtdServidores(Integer qtdServidores) {
		this.qtdServidores = qtdServidores;
	}

	public Set<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
	}

	public Set<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(Set<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setStatus(boolean ativo) {
		this.setAtivo(ativo);
	}
	
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((caracteristica == null) ? 0 : caracteristica.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((qtdServidores == null) ? 0 : qtdServidores.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(!(obj instanceof Modalidade))
            return false;
		
		Modalidade other = (Modalidade) obj;
		if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
		if (!Objects.equals(this.getCaracteristica(), other.getCaracteristica())) {
            return false;
        }
		if (!Objects.equals(this.getClass(), other.getClass())) {
            return false;
        }
		if (!Objects.equals(this.getNome(), other.getNome())) {
            return false;
        }
		if (!Objects.equals(this.getQtdServidores(), other.getQtdServidores())) {
            return false;
        }
		if (!Objects.equals(this.getCor(), other.getCor())) {
            return false;
        }
		
		return true;
	}

	@Override
	public String toString() {
		return "Modalidade [id=" + id + ", nome=" + nome + ", caracteristica=" + caracteristica.getNome() + ", qtdServidores="
				+ qtdServidores + ", recursos=" + recursos.size() + ", instituicoes=" + instituicoes.size() + ", ativo=" + ativo
				+ "]";
	}	
}
