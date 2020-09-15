package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "OBJETOS")
@SuppressWarnings("serial")
public class Objeto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@NotBlank()
	@Size(min= 3, max=255)
	@Column(name = "DESCRICAO", nullable = false)
	private String descricaoObjeto;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPIFICACAO_OBJETO")
	private TipificacaoObjeto tipo;

	@JsonIgnore
	@ManyToOne(targetEntity = RegistroOcorrencia.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_REGISTRO_OCORRENCIA", nullable = true)
	private RegistroOcorrencia regitroOcorrencia;

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	public Objeto() {
		this.ativo = true;
	}

	public Objeto(Long id, @NotNull String descricao, TipificacaoObjeto tipo,
			@NotNull RegistroOcorrencia regitroOcorrencia, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.descricaoObjeto = descricao;
		this.tipo = tipo;
		this.regitroOcorrencia = regitroOcorrencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoObjeto() {
		return descricaoObjeto;
	}

	public void setDescricaoObjeto(String descricao) {
		this.descricaoObjeto = descricao;
	}

	public TipificacaoObjeto getTipo() {
		return tipo;
	}

	public void setTipo(TipificacaoObjeto tipo) {
		this.tipo = tipo;
	}

	public RegistroOcorrencia getRegitroOcorrencia() {
		return regitroOcorrencia;
	}

	public void setRegitroOcorrencia(RegistroOcorrencia regitroOcorrencia) {
		this.regitroOcorrencia = regitroOcorrencia;
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
		result = prime * result + ((descricaoObjeto == null) ? 0 : descricaoObjeto.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((regitroOcorrencia == null) ? 0 : regitroOcorrencia.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Objeto other = (Objeto) obj;
		if (ativo != other.ativo)
			return false;
		if (descricaoObjeto == null) {
			if (other.descricaoObjeto != null)
				return false;
		} else if (!descricaoObjeto.equals(other.descricaoObjeto))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (regitroOcorrencia == null) {
			if (other.regitroOcorrencia != null)
				return false;
		} else if (!regitroOcorrencia.equals(other.regitroOcorrencia))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Objeto [id=" + id + ", descricao=" + descricaoObjeto + ", tipo=" + tipo + ", regitroOcorrencia="
				+ regitroOcorrencia + ", ativo=" + ativo + "]";
	}
}
