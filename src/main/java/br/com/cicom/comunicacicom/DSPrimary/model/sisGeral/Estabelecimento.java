package br.com.cicom.comunicacicom.DSPrimary.model.sisGeral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Endereco;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;

/**
 *
 * @author Estatistica2
 * 
 */
@Entity
@Table(name = "ESTABELECIMENTO")
@SuppressWarnings("serial")
public class Estabelecimento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Size(min = 3, max = 60)
	@Column(name = "NOME")
	private String nome;

	@NotNull
	@OneToOne(targetEntity = Endereco.class)
	@JoinColumn(name = "ENDERECO")
	private Endereco endereco;

	@OneToOne(targetEntity = Email.class)
	@JoinColumn(name = "Email")
	private Email email;

	@NotNull
	@JsonIgnore
	@Column(name = "SENHA_EMAIL")
	private String senhaDoEmail;

	@JsonIgnoreProperties({"cidade.estabelecimento"})
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(targetEntity = Cidade.class, mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Cidade> cidades = new ArrayList<>();

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity = Instituicao.class)
	@JoinTable(name = "ESTABELECIMENTO_INSTITUICOES", joinColumns = {
			@JoinColumn(name = "ESTABELECIMENTO_ID") }, inverseJoinColumns = { @JoinColumn(name = "INSTITUICAO_ID") })
	private List<Instituicao> instituicoes = new ArrayList<>();

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	public Estabelecimento() {
		this.ativo = true;
	}

	public Estabelecimento(Long id, @NotNull @Size(min = 3, max = 60) String nome, @NotNull Endereco endereco,
			Email email, @NotNull String senhaDoEmail, List<Cidade> cidades, List<Instituicao> instituicoes, @NotNull boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
		this.senhaDoEmail = senhaDoEmail;
		this.cidades = cidades;
		this.instituicoes = instituicoes;
		this.ativo = ativo;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> unidades) {
		this.instituicoes = unidades;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getSenhaDoEmail() {
		return senhaDoEmail;
	}

	public void setSenhaDoEmail(String senhaDoEmail) {
		this.senhaDoEmail = Base64.getEncoder().encodeToString(senhaDoEmail.getBytes());
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setStatus(boolean ativo) {
		this.ativo = ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cidades == null) ? 0 : cidades.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituicoes == null) ? 0 : instituicoes.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senhaDoEmail == null) ? 0 : senhaDoEmail.hashCode());
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
		Estabelecimento other = (Estabelecimento) obj;
		if (ativo != other.ativo)
			return false;
		if (cidades == null) {
			if (other.cidades != null)
				return false;
		} else if (!cidades.equals(other.cidades))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituicoes == null) {
			if (other.instituicoes != null)
				return false;
		} else if (!instituicoes.equals(other.instituicoes))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senhaDoEmail == null) {
			if (other.senhaDoEmail != null)
				return false;
		} else if (!senhaDoEmail.equals(other.senhaDoEmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estabelecimento [id=" + id 
				+ ", nome=" + nome 
				+ ", cidades=" + cidades.size() 
				+ ", instituicoes=" + instituicoes.size() 
				+ ", ativo=" + ativo + "]";
	}
}