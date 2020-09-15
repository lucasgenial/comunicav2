package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_RECURSO")
@SuppressWarnings("serial")
public class Recurso implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@Basic
	@NotNull
	@Column(name = "NOME", unique = true)
	private String nome;

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;

	@Basic
	@Column(name = "FUNCIONAMENTO")
	private boolean funcionamento;

	@Basic
	@Column(name = "REFERENCIA")
	private String referencia;

	/*
	 * CONSTRUTORES
	 */
	public Recurso() {
		this.setStatus(true);
	}

	public Recurso(Long id, @NotNull String nome, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setStatus(boolean ativo) {
		this.setAtivo(ativo);
	}

	public boolean isFuncionamento() {
		return funcionamento;
	}

	public void setFuncionamento(boolean funcionamento) {
		this.funcionamento = funcionamento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + (funcionamento ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recurso other = (Recurso) obj;
		if (ativo != other.ativo)
			return false;
		if (funcionamento != other.funcionamento)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recurso [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ "ativo=" + ativo + ", funcionamento=" + funcionamento + ", "
				+ (referencia != null ? "referencia=" + referencia : "") + "]";
	}

}
