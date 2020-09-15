package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "ENDERECO")
@SuppressWarnings("serial")
public class Endereco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
    
	@NotNull
    @OneToOne(targetEntity = Cidade.class)
    @JoinColumn(name = "CIDADE")
    private Cidade cidade;
    
    @NotNull
    @OneToOne(targetEntity = Bairro.class)
    @JoinColumn(name = "BAIRRO")
    private Bairro bairro;
            
    @NotNull
    @Column(name="RUA")
    private String rua;
    
    @Column(name="NUMERO")
    private String numero;
        
    @Column(name="REFERENCIA")
    private String referencia;

	public Endereco() {
	}

	public Endereco(Long id, @NotNull Cidade cidade, @NotNull Bairro bairro, @NotNull String rua, String numero, String referencia) {
		super();
		this.id = id;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.referencia = referencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua.replaceAll("\\coma", "/");
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia.replaceAll("\\coma", "/");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(this.getBairro());
		result = prime * result + Objects.hashCode(this.getCidade());
		result = prime * result + Objects.hashCode(this.getId());
		result = prime * result + Objects.hashCode(this.getNumero());
		result = prime * result + Objects.hashCode(this.getReferencia());
		result = prime * result + Objects.hashCode(this.getRua());
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
		Endereco other = (Endereco) obj;
		
		if (!Objects.equals(this.getBairro(), other.getBairro())) {
			return false;
		}
		
		
		if (!Objects.equals(this.getCidade(), other.getCidade())) {
			return false;
		}
		
		if (!Objects.equals(this.getId(), other.getId())) {
			return false;
		}
		
		if (!Objects.equals(this.getNumero(), other.getNumero())) {
			return false;
		}
		
		if (!Objects.equals(this.getReferencia(), other.getReferencia())) {
			return false;
		}
		
		if (!Objects.equals(this.getRua(), other.getRua())) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [" 
				+ (id != null ? "id=" + id + ", " : "") 
				+ (cidade != null ? "cidade=" + cidade.getNome() + ", " : "")
				+ (bairro != null ? "bairro=" + bairro + ", " : "")
				+ (rua != null ? "rua=" + rua + ", " : "")
				+ (numero != null ? "numero=" + numero + ", " : "")
				+ (referencia != null ? "referencia=" + referencia : "") + "]";
	}	
	
}
