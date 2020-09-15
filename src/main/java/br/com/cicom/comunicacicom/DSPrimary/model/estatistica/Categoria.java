package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "CATEGORIA")
@SuppressWarnings("serial")
public class Categoria implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "NOME", unique = true)
	private String nome;

	@JsonIgnore
	@OneToMany(targetEntity = Tipificacao.class, mappedBy = "categoria", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Tipificacao> tiposOcorrencia = new ArrayList<>();

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;
	
	@Column(name = "COR_GRAFICO")
	private String cor;

	public Categoria() {
		this.ativo = true;
	}

	public Categoria(@NotNull String nome, List<Tipificacao> tiposOcorrencia, @NotNull boolean ativo) {
		this.nome = nome;
		this.tiposOcorrencia = tiposOcorrencia;
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

	public List<Tipificacao> getTiposOcorrencia() {
		return tiposOcorrencia;
	}

	public void setTiposOcorrencia(List<Tipificacao> tiposOcorrencia) {
		this.tiposOcorrencia = tiposOcorrencia;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setStatus(boolean value) {
		this.setAtivo(value);
	}
	
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	

	@Override
	public String toString() {
		return "Categoria [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (tiposOcorrencia != null ? "tiposOcorrencia=" + tiposOcorrencia + ", " : "") + "ativo=" + ativo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tiposOcorrencia == null) ? 0 : tiposOcorrencia.hashCode());
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
		Categoria other = (Categoria) obj;
		if (ativo != other.ativo)
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
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
		if (tiposOcorrencia == null) {
			if (other.tiposOcorrencia != null)
				return false;
		} else if (!tiposOcorrencia.equals(other.tiposOcorrencia))
			return false;
		return true;
	}
}