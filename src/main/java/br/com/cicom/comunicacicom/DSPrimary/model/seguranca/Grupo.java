package br.com.cicom.comunicacicom.DSPrimary.model.seguranca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Estatistica2
 * 
 */
@Entity
@Table(name = "GRUPOS")
@SuppressWarnings("serial")
public class Grupo implements Serializable {
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

	@ManyToMany(targetEntity=Permissao.class, cascade = CascadeType.ALL)
	@JoinTable(name = "GRUPOS_PERMISSOES", joinColumns = {
			@JoinColumn(name = "ID_GRUPO")}, inverseJoinColumns = {
			@JoinColumn(name = "ID_PERMISSOES") })
	private List<Permissao> permissoes = new ArrayList<>();
	
	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;

	public Grupo() {
		this.ativo=true;
	}

	public Grupo(Long id, @NotNull String nome, @NotNull String descricao, List<Permissao> permissoes, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.permissoes = permissoes;
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
		this.nome = value;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String value) {
		this.descricao = value;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> value) {
		this.permissoes = value;
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
		result = (int) (prime * result + id);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissoes == null) ? 0 : permissoes.hashCode());
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
		Grupo other = (Grupo) obj;
		if (ativo != other.ativo)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (permissoes == null) {
			if (other.permissoes != null)
				return false;
		} else if (!permissoes.equals(other.permissoes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", nome=" + nome + ", descricao=" + descricao
				+ ", ativo=" + ativo
				+ ", permissoes=" + permissoes
				+ "]";
	}
}