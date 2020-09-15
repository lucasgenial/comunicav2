package br.com.cicom.comunicacicom.DSPrimary.model.seguranca;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lucas Matos e Souza
 * 
 */
@Entity
@Table(name = "PERMISSAO")
@SuppressWarnings("serial")
public class Permissao implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "NOME")
	private String nome;

	@NotNull
	@Column(name = "DESCRICAO")
	private String descricao;
	
	
	@Column(name = "ATIVO")
	private boolean ativo;

	public Permissao() {
		this.ativo=true;
	}

	public Permissao(Long id, @NotNull String nome, @NotNull String descricao, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String value) {
		this.nome = value.toUpperCase();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String value) {
		this.descricao = value.toUpperCase();
	}

	public void setAtivo(boolean value) {
		this.ativo = value;
	}

	public boolean getAtivo() {
		return this.ativo;
	}
	
	public boolean isAtivo() {
		return this.ativo;
	}
	
	public void setStatus(boolean value) {
		this.setAtivo(value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Permissao other = (Permissao) obj;
		if (ativo != other.ativo)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		return true;
	}

	@Override
	public String toString() {
		return "Permissao [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", ativo=" + ativo + "]";
	}
}