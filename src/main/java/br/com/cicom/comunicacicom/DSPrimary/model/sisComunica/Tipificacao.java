package br.com.cicom.comunicacicom.DSPrimary.model.sisComunica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "TIPIFICACAO")
@SuppressWarnings("serial")
public class Tipificacao implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "NOME", unique = true)
	private String nome;

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	@ManyToOne(targetEntity = Categoria.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORIA_ID")
	private Categoria categoria;
	
	@Column(name = "COR_GRAFICO")
	private String cor;

	public Tipificacao() {
		this.ativo = true;
	}

	public Tipificacao(Long id, @NotNull String nome, Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
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
		this.ativo = ativo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
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
		Tipificacao other = (Tipificacao) obj;
		if (ativo != other.ativo)
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
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
		if (cor == null) {
			if (other.nome != null)
				return false;
		} else if (!cor.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tipificacao [id=" + id + ", nome=" + nome + ", ativo=" + ativo + "]";
	}
}