package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "BAIRRO")
@SuppressWarnings("serial")
public class Bairro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
    
	@NotNull
	@Size(min=3, max=60)
	@Column(name = "NOME")
    private String nome;
    
	@NotNull
    @ManyToOne(targetEntity = Localidade.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "LOCALIDADE_ID")
    private Localidade localidade;
    
    @NotNull
    @Column(name="STATUS")
    private boolean ativo;

    public Bairro() {
    	this.ativo=true;
    }
    
    public Bairro(@NotNull @Size(min = 3, max = 60) String nome, @NotNull Localidade localidade) {
		this.nome = nome;
		this.localidade = localidade;
	}

    public Bairro(@NotNull @Size(min = 3, max = 60) String nome, @NotNull Localidade localidade, boolean status) {
		this.nome = nome;
		this.localidade = localidade;
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

	public Localidade getLocalidade() {
		return localidade;
	}

	public String getCidade() {
		return localidade.getCidade().getNome();
	}
	
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
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
		if (!(obj instanceof Bairro))
			return false;
		
		Bairro other = (Bairro) obj;
		
		if (!Objects.equals(this.isAtivo(), other.isAtivo())) {
			return false;
		}
		
		if (!Objects.equals(this.getNome(), other.getNome())) {
			return false;
		}
		
		if (!Objects.equals(this.getId(), other.getId())) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Bairro [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (localidade != null ? "localidade=" + localidade + ", " : "") + "ativo=" + ativo + "]";
	}
}
