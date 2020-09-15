package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.EstadoOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;

@Entity
@Table(name = "REGISTRO_OCORRENCIA")
@SuppressWarnings("serial")
public class RegistroOcorrencia implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@JsonIgnoreProperties("registroOcorrencia")
	@OneToOne(targetEntity = Ocorrencia.class)
	@JoinColumn(name = "OCORRENCIA_ID")
	private Ocorrencia ocorrencia;
	
	@OneToMany(targetEntity = Pessoa.class, mappedBy = "regitroOcorrencia", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pessoa> envolvidos = new ArrayList<>();

	@OneToMany(targetEntity = Objeto.class, mappedBy = "regitroOcorrencia", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Objeto> ObjetosEnvolvidos = new ArrayList<>();

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	public RegistroOcorrencia() {
		ativo = true;
	}

	public RegistroOcorrencia(Long id, @NotBlank Ocorrencia ocorrencia,List<Pessoa> envolvidos,
			List<Objeto> objetosEnvolvidos, @NotNull EstadoOcorrencia estadoOcorrencia, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.ocorrencia = ocorrencia;
		this.envolvidos = envolvidos;
		ObjetosEnvolvidos = objetosEnvolvidos;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public List<Pessoa> getEnvolvidos() {
		return envolvidos;
	}

	public void setEnvolvidos(List<Pessoa> envolvidos) {
		this.envolvidos = envolvidos;
	}

	public List<Objeto> getObjetosEnvolvidos() {
		return ObjetosEnvolvidos;
	}

	public void setObjetosEnvolvidos(List<Objeto> objetosEnvolvidos) {
		ObjetosEnvolvidos = objetosEnvolvidos;
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
		result = prime * result + ((ObjetosEnvolvidos == null) ? 0 : ObjetosEnvolvidos.hashCode());
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((envolvidos == null) ? 0 : envolvidos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ocorrencia == null) ? 0 : ocorrencia.hashCode());
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
		RegistroOcorrencia other = (RegistroOcorrencia) obj;
		if (ObjetosEnvolvidos == null) {
			if (other.ObjetosEnvolvidos != null)
				return false;
		} else if (!ObjetosEnvolvidos.equals(other.ObjetosEnvolvidos))
			return false;
		if (ativo != other.ativo)
			return false;
		if (envolvidos == null) {
			if (other.envolvidos != null)
				return false;
		} else if (!envolvidos.equals(other.envolvidos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ocorrencia == null) {
			if (other.ocorrencia != null)
				return false;
		} else if (!ocorrencia.equals(other.ocorrencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroOcorrencia [id=" + id + ", ocorrencia=" + ocorrencia
				+ ", envolvidos=" + envolvidos.size()
				+ ", ObjetosEnvolvidos=" + ObjetosEnvolvidos.size()
				+ ", ativo=" + ativo + "]";
	}

}
