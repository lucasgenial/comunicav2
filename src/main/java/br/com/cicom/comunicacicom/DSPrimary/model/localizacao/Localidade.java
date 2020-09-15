package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Estatistica2
 * 
 */

@Entity
@Table(name = "LOCALIDADE")
public class Localidade implements Serializable, Comparator<Localidade> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "NOME")
	private String nome;

	@NotNull
	@JsonIgnoreProperties({ "localidades" })
	@ManyToOne(targetEntity = Cidade.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "CIDADE_ID")
	private Cidade cidade;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(targetEntity = Bairro.class, mappedBy = "localidade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Bairro> bairros = new ArrayList<>();

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	public Localidade() {
		this.ativo = true;
	}

	public Localidade(Long id, @NotNull String nome, List<Bairro> bairros, @NotNull Cidade cidade,
			@NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.bairros = bairros;
		this.cidade = cidade;
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

	public List<Bairro> getBairros() {
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
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
		if (!(obj instanceof Localidade))
			return false;

		Localidade other = (Localidade) obj;

		if (!Objects.equals(this.isAtivo(), other.isAtivo())) {
			return false;
		}
		if (!Objects.equals(this.getClass(), other.getClass())) {
			return false;
		}
		if (!Objects.equals(this.getId(), other.getId())) {
			return false;
		}
		if (!Objects.equals(this.getNome(), other.getNome())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Localidade [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (cidade != null ? "cidade=" + cidade + ", " : "") + "ativo=" + ativo + "]";
	}
	
	@Override
	public int compare(Localidade a, Localidade b) {
		return a.getNome().toUpperCase().compareTo(b.getNome().toUpperCase());
	}

}