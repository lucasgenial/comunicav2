package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "TIPO_ENVOLVIMENTO")
@SuppressWarnings("serial")
public class TipoEnvolvimento implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
    @Column(name = "NOME", unique = true)
    private String nome;
    
	@NotNull
    @Column(name="STATUS")
    private boolean ativo;
    
    public TipoEnvolvimento() {
    	this.ativo = true;
    }

    public TipoEnvolvimento(Long id, String nome, boolean status) {
        this.id = id;
        this.nome = nome;
        this.ativo = status;
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
		if (getClass() != obj.getClass())
			return false;
		TipoEnvolvimento other = (TipoEnvolvimento) obj;
		if (ativo != other.ativo)
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
		return "TipoEnvolvimento [id=" + id + ", nome=" + nome + ", ativo=" + ativo + "]";
	}
}
