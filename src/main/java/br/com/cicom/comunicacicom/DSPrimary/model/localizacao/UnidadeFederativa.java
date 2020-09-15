package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UNIDADE_FEDERATIVA")
@SuppressWarnings("serial")
public class UnidadeFederativa implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	
	@Column(name = "NOME")
	@Size(min=5, max=50)
	private String nome;
		
	@JsonIgnore
	@OneToMany(targetEntity = Cidade.class, mappedBy = "uf", cascade = CascadeType.ALL)
	private List<Cidade> cidades = new ArrayList<>();
		
	
	
	@Column(name = "STATUS")
	private boolean ativo;
	
	public UnidadeFederativa() {
		this.ativo = true;
	}

	public UnidadeFederativa(Long id, @NotNull @Size(min = 5, max = 50) String nome, List<Cidade> cidades,
			@NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cidades = cidades;
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
	
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setStatus(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cidades == null) ? 0 : cidades.hashCode());
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
		UnidadeFederativa other = (UnidadeFederativa) obj;
		if (ativo != other.ativo)
			return false;
		if (cidades == null) {
			if (other.cidades != null)
				return false;
		} else if (!cidades.equals(other.cidades))
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
		return "Unidade Federativa = " + nome;
	}
	
}
