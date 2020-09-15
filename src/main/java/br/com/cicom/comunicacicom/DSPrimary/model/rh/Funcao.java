package br.com.cicom.comunicacicom.DSPrimary.model.rh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;


/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "FUNCAO")
@SuppressWarnings("serial")
public class Funcao implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	//@NotNull
	@Size(min=3, max=120)
    @Column(name = "NOME", unique = true)
    private String nome;
	
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @NotNull
    @Column(name="PRIORIDADE_MESA")
    private boolean prioridade;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_FUNCAO")
	private TipoFuncao tipoFuncao;
    
	@NotNull
    @Column(name="STATUS")
    private boolean ativo;

	public Funcao() {
		this.ativo = true;
	}

	public Funcao(Long id, @Size(min = 3, max = 120) String nome, String descricao,
			@NotNull boolean prioridade, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public TipoFuncao getTipoFuncao() {
		return tipoFuncao;
	}

	public void setTipoFuncao(TipoFuncao tipoFuncao) {
		this.tipoFuncao = tipoFuncao;
	} 
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isPrioridade() {
		return prioridade;
	}

	public void setPrioridade(boolean prioridade) {
		this.prioridade = prioridade;
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
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (prioridade ? 1231 : 1237);
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
		Funcao other = (Funcao) obj;
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (prioridade != other.prioridade)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcao [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", prioridade=" + prioridade
				+ ", ativo=" + ativo + "]";
	}
	
	
}