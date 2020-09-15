package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_CARACTERISTICA")
@SuppressWarnings("serial")
public class Caracteristica implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

	@Basic
    @Column(name = "NOME", unique = true)
    private String nome;
    
	@NotNull
    @Column(name = "ATIVO")
    private boolean ativo;

    /*
    CONSTRUTORES
     */
    public Caracteristica() {
        this.setStatus(true);
    }
    
	public Caracteristica(Long id, @NotNull String nome, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setStatus(boolean ativo) {
		this.setAtivo(ativo);
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(!(obj instanceof Caracteristica)){
            return false;
        }
        
        final Caracteristica other = (Caracteristica) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!Objects.equals(this.getNome(), other.getNome())) {
            return false;
        }
        if (!Objects.equals(this.isAtivo(), other.isAtivo())) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Caracteristica [id=" + id + ", nome=" + nome + ", ativo=" + ativo + "]";
	}
}
