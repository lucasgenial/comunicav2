package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_SERVIDOR")
@SuppressWarnings("serial")
public class ServidorExterno implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	@NotNull
	@Size(min=3, max=120)
	@Column(name = "NOME", unique = false)
	private String nome;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER, targetEntity = Instituicao.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "INSTITUICAO")
	private Instituicao instituicao;
	
	@NotNull
	@OneToOne(targetEntity = Hierarquia.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "GRAU_HIERARQUICO")
	private Hierarquia hierarquia;
		
	@Column(name = "MATRICULA", unique = true)
	private String matricula;

	@NotNull
	@Column(name = "STATUS" )
	private boolean ativo;
	
	public ServidorExterno() {
		this.ativo = true;
	}
	
	public ServidorExterno(Long id, @NotNull @Size(min = 3, max = 120) String nome, @NotNull Instituicao instituicao,
			@NotNull Hierarquia hierarquia, String matricula, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.instituicao = instituicao;
		this.hierarquia = hierarquia;
		this.matricula = matricula;
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

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Hierarquia getHierarquia() {
		return hierarquia;
	}

	public void setHierarquia(Hierarquia hierarquia) {
		this.hierarquia = hierarquia;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((hierarquia == null) ? 0 : hierarquia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		ServidorExterno other = (ServidorExterno) obj;
		if (ativo != other.ativo)
			return false;
		if (hierarquia == null) {
			if (other.hierarquia != null)
				return false;
		} else if (!hierarquia.equals(other.hierarquia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
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
		return "ServidorExterno [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (instituicao != null ? "instituicao=" + instituicao + ", " : "")
				+ (hierarquia != null ? "hierarquia=" + hierarquia.getNome() + ", " : "")
				+ (matricula != null ? "matricula=" + matricula + ", " : "")
				+ "ativo=" + ativo + "]";
	}
	


}