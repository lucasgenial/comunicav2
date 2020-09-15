package br.com.cicom.comunicacicom.DSPrimary.model.sisGeral;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Modalidade;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "UNIDADE")
@SuppressWarnings("serial")
public class Unidade implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Size(min = 3, max = 120)
	@Column(name = "NOME", unique = true)
	private String nome;

	@NotNull
	@OneToOne(targetEntity = Cidade.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CIDADE")
	private Cidade cidade;

	@ManyToOne(targetEntity = Instituicao.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTITUICAO")
	private Instituicao instituicao;

	@OneToOne(targetEntity = Email.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "Email")
	private Email email;

	@ManyToOne(targetEntity = Estabelecimento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTABELECIMENTO_ID")
	private Estabelecimento estabelecimento;

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	@Column(name = "COR_GRAFICO")
	private String cor;

	public Unidade() {
		this.ativo = true;
	}

	public Unidade(Long id, @NotNull @Size(min = 3, max = 120) String nome, @NotNull Cidade cidade,
			Instituicao instituicao, Set<Modalidade> modalidades, Email email, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
		this.instituicao = instituicao;
		this.email = email;
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

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
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
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
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
		Unidade other = (Unidade) obj;
		if (ativo != other.ativo)
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
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
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unidade [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (cidade != null ? "cidade=" + cidade + ", " : "") + ", ativo=" + ativo + ", cor="+ cor +"]";
	}

}