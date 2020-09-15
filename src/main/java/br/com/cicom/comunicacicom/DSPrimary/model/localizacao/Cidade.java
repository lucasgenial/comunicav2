package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

/**
 *
 * @author Estatistica2
 * 
 */
@Entity
@Table(name = "CIDADE")
public class Cidade implements Serializable, Comparable<Cidade> {

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

	@JsonIgnoreProperties({ "cidade" })
	@OneToMany(targetEntity = Localidade.class, mappedBy = "cidade", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Localidade> localidades = new HashSet<>();

	@JsonIgnoreProperties({ "cidades", "endereco" })
	@ManyToOne(targetEntity = Estabelecimento.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTABELECIMENTO_ID")
	private Estabelecimento estabelecimento;

	@NotNull
	@ManyToOne(targetEntity = UnidadeFederativa.class)
	@JoinColumn(name = "UF")
	private UnidadeFederativa uf;

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	@Column(name = "COR_GRAFICO")
	private String cor;

	public Cidade() {
		this.ativo = true;
	}

	public Cidade(Long id, @NotNull String nome, Set<Localidade> localidades, String cor, @NotNull boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.localidades = localidades;
		this.cor = cor;
		this.ativo = ativo;
	}

	public Cidade(Long id, @NotNull String nome, Set<Localidade> localidades, Estabelecimento estabelecimento,
			String cor, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.localidades = localidades;
		this.cor = cor;
		this.estabelecimento = estabelecimento;
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
		this.nome = nome;
	}

	public Set<Localidade> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(Set<Localidade> localidades) {
		this.localidades = localidades;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setStatus(boolean ativo) {
		this.ativo = ativo;
	}

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cidade))
			return false;

		Cidade other = (Cidade) obj;

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
		if (!Objects.equals(this.getUf(), other.getUf())) {
			return false;
		}
		if (!Objects.equals(this.getCor(), other.getCor())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Cidade [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (estabelecimento != null ? "estabelecimento=" + estabelecimento + ", " : "")
				+ (uf != null ? "uf=" + uf + ", " : "") + "ativo=" + ativo + ", " + (cor != null ? "cor=" + cor : "")
				+ "]";
	}

	@Override
	public int compareTo(Cidade cid) {
		return this.getNome().compareTo(cid.getNome());
	}
}