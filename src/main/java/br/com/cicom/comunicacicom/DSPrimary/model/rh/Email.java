package br.com.cicom.comunicacicom.DSPrimary.model.rh;

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
@Table(name = "EMAIL")
@SuppressWarnings("serial")
public class Email implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "NOME", unique = false)
	private String nome;

	@NotNull
	@Column(name = "EMAIL", unique = false)
	private String enderecoDeEmail;

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	public Email() {
		this.ativo = true;
	}

	public Email(Long id, String nome, String email, boolean status) {
		this.id = id;
		this.nome = nome;
		this.enderecoDeEmail = email;
		this.ativo = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public String getEnderecoDeEmail() {
		return enderecoDeEmail;
	}

	public void setEnderecoDeEmail(String value) {
		this.enderecoDeEmail = value;
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
		result = prime * result + ((enderecoDeEmail == null) ? 0 : enderecoDeEmail.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		Email other = (Email) obj;
		if (ativo != other.ativo) {
			return false;
		}
		if (enderecoDeEmail == null) {
			if (other.enderecoDeEmail != null) {
				return false;
			}
		} else if (!enderecoDeEmail.equals(other.enderecoDeEmail)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", nome=" + nome + ", enderecoDeEmail=" + enderecoDeEmail + ", ativo=" + ativo + "]";
	}
}