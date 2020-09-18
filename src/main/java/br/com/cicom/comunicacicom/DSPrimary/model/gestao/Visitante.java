package br.com.cicom.comunicacicom.DSPrimary.model.gestao;

import java.io.Serializable;
import java.net.URI;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Anderson Kroger
 */
@Entity
@Table(name = "VISITANTE")
@SuppressWarnings("serial")
public class Visitante implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "NOME", unique = false)
	private String nome="";

	@NotNull
	@Column(name = "CPF", unique = true)
	private String cpf;

	
	@Column(name = "FOTOGRAFIA", nullable=true, columnDefinition="mediumblob")
	private byte[] fotografia = null;
	
	public Visitante() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public byte[] getFotografia() {
		return fotografia;
	}
	
	public void setFotografia(byte[] fotografia) {
		this.fotografia = fotografia;
	}
	
	public void setFotografiaUri(URI fotografia) {
		byte[] fot = Base64.getDecoder().decode(fotografia.toString().replace("data:image/jpeg;base64,", ""));
		this.fotografia = fot;
	}
	
	public String getFotografiaUri() {
		return "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(this.getFotografia());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Visitante other = (Visitante) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
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
		return "Visitante [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
	}
	
	

}
