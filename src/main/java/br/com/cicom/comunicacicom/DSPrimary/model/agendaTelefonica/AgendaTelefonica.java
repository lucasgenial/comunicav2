package br.com.cicom.comunicacicom.DSPrimary.model.agendaTelefonica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AGENDA_TELEFONICA")
@SuppressWarnings("serial")
public class AgendaTelefonica implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	@NotNull
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "NUMERO")
	private String numero;
	
	@Column(name = "RAMAL")
	private String ramal;
	
	

	public AgendaTelefonica() {
		super();
	}



	public AgendaTelefonica(Long id, @NotNull String nome, String numero, String ramal) {
		super();
		this.id = id;
		this.nome = nome;
		this.numero = numero;
		this.ramal = ramal;
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



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getRamal() {
		return ramal;
	}



	public void setRamal(String ramal) {
		this.ramal = ramal;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((ramal == null) ? 0 : ramal.hashCode());
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
		AgendaTelefonica other = (AgendaTelefonica) obj;
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
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (ramal == null) {
			if (other.ramal != null)
				return false;
		} else if (!ramal.equals(other.ramal))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "AgendaTelefonica [id=" + id + ", nome=" + nome + ", numero=" + numero + ", ramal=" + ramal + "]";
	}

	
	
	

}
