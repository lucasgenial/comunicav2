package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ENDERECO_SERVIDOR")
@SuppressWarnings("serial")
public class EnderecoServidor  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	@NotNull
	@JsonIgnore 
    @OneToOne(targetEntity = UnidadeFederativa.class)
    @JoinColumn(name = "Uf_servidor")
    private UnidadeFederativa UfServidor;
	
	@NotNull
	@JsonIgnore 
    @OneToOne(targetEntity = Cidade.class)
    @JoinColumn(name = "CIDADE")
    private Cidade cidade;
    
    @NotNull
    @JsonIgnore 
    @JoinColumn(name = "BAIRRO")
    private String bairro;
            
    @NotNull
    @Column(name="RUA")
    private String rua;
    
    @Column(name="NUMERO")
    private String numero;
        
    @Column(name="REFERENCIA")
    private String referencia;

	public EnderecoServidor() {
		super();
	}

	public EnderecoServidor(Long id, @NotNull UnidadeFederativa ufServidor, @NotNull Cidade cidade,
			@NotNull String bairro, @NotNull String rua, String numero, String referencia) {
		super();
		this.id = id;
		this.UfServidor = ufServidor;
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

	public UnidadeFederativa getUfServidor() {
		return UfServidor;
	}

	public void setUfServidor(UnidadeFederativa ufServidor) {
		UfServidor = ufServidor;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
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
		this.referencia = referencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((UfServidor == null) ? 0 : UfServidor.hashCode());
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
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
		EnderecoServidor other = (EnderecoServidor) obj;
		if (UfServidor == null) {
			if (other.UfServidor != null)
				return false;
		} else if (!UfServidor.equals(other.UfServidor))
			return false;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
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
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		if (rua == null) {
			if (other.rua != null)
				return false;
		} else if (!rua.equals(other.rua))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EnderecoServidor [id=" + id + ", UfServidor=" + UfServidor + ", cidade=" + cidade + ", bairro=" + bairro
				+ ", rua=" + rua + ", numero=" + numero + ", referencia=" + referencia + "]";
	}
    
}
