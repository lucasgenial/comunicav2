package br.com.cicom.comunicacicom.DSPrimary.model.sisGeral;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Modalidade;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "INSTITUICAO")
@SuppressWarnings("serial")
public class Instituicao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Size(min = 2, max = 120)
	@Column(name = "NOME")
	private String nome;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REMOVE }, targetEntity = Modalidade.class)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "INSTITUICAO_MODALIDADE", joinColumns = {
			@JoinColumn(name = "MODALIDADE_ID") }, inverseJoinColumns = { @JoinColumn(name = "INSTITUICAO_ID") })
	private List<Modalidade> modalidades = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Hierarquia.class, mappedBy = "instituicao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Hierarquia> hierarquias = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Unidade.class, mappedBy = "instituicao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Unidade> unidades = new ArrayList<>();
	
	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;
	
	@Column(name = "COR_GRAFICO")
	private String cor;

	public Instituicao() {
		this.ativo = true;
	}

	public Instituicao(Long id, @NotNull @Size(min = 3, max = 120) String nome, List<Hierarquia> hierarquias, List<Unidade> unidades,
			@NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.hierarquias = hierarquias;
		this.ativo = ativo;
		this.unidades = unidades;
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

	public List<Hierarquia> getHierarquias() {
		return hierarquias;
	}

	public void setHierarquias(List<Hierarquia> hierarquias) {
		this.hierarquias = hierarquias;
	}

	public List<Modalidade> getModalidades() {
		return modalidades;
	}

	public void setModalidades(List<Modalidade> modalidades) {
		this.modalidades = modalidades;
	}
	
	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public boolean isAtivo() {
		return ativo;
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
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((unidades == null) ? 0 : unidades.hashCode());
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
		Instituicao other = (Instituicao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}else if (id.equals(other.id)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Instituicao [id=" + id + ", nome=" + nome + ", modalidades=" + modalidades.size() + ", hierarquias="
				+ hierarquias.size() + ", unidades=" + unidades.size() + ", ativo=" + ativo + ", cor=" + cor + "]";
	}
}