package br.com.cicom.comunicacicom.DSPrimary.model.rh;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;


/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "HIERARQUIA")
@SuppressWarnings("serial")
public class Hierarquia implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Size(min=3, max=120)
    @Column(name = "NOME")
    private String nome;
	
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @NotNull
	@JsonIgnore
	@ManyToOne(targetEntity = Instituicao.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "instituicao_id")
	private Instituicao instituicao;
    
	@NotNull
    @Column(name="STATUS")
    private boolean ativo;

	public Hierarquia() {
		this.ativo = true;
	}

	public Hierarquia(Long id, @NotNull @Size(min = 3, max = 120) String nome, String descricao,
			Instituicao instituicao, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.instituicao = instituicao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setStatus(boolean ativo) {
		this.ativo = ativo;
	}
	

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituicao == null) ? 0 : instituicao.hashCode());
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
		Hierarquia other = (Hierarquia) obj;
		if (ativo != other.ativo)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hierarquia [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", instituicao=" + instituicao
				+ ", ativo=" + ativo + "]";
	}

}