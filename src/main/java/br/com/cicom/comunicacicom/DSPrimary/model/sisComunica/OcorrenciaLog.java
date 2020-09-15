package br.com.cicom.comunicacicom.DSPrimary.model.sisComunica;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ocorrencia_log")
@SuppressWarnings("serial")
public class OcorrenciaLog implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@ManyToOne(targetEntity = Ocorrencia.class,  cascade=CascadeType.PERSIST)
	@JoinColumn(name = "ocorrencia")
	@JsonIgnore
	private Ocorrencia ocorrencia;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "datahora", nullable = false)
	private LocalDateTime data;
	
	@Size(min = 0, max = 100)
	@Column(name = "descricao")
	private String descricao;

	@Size(min = 0, max = 100)
	@Column(name = "operador")
	private String operador;
	
	@Size(min = 0, max = 100)
	@Column(name = "anotacao")
	private String anotacao;

	public OcorrenciaLog() {
		super();
	}

	public OcorrenciaLog(Long id,
			Ocorrencia ocorrencia, 
			@NotNull LocalDateTime data,
			@NotNull @Size(min = 0, max = 100) String descricao, @Size(min = 0, max = 100) String operador,
			@Size(min = 0, max = 100) String anotacao) {
		super();
		this.id = id;
		this.ocorrencia = ocorrencia;
		this.data = data;
		this.descricao = descricao;
		this.operador = operador;
		this.anotacao = anotacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anotacao == null) ? 0 : anotacao.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ocorrencia == null) ? 0 : ocorrencia.hashCode());
		result = prime * result + ((operador == null) ? 0 : operador.hashCode());
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
		OcorrenciaLog other = (OcorrenciaLog) obj;
		if (anotacao == null) {
			if (other.anotacao != null)
				return false;
		} else if (!anotacao.equals(other.anotacao))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		return true;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	@Override
	public String toString() {
		return "OcorrenciaLog [" 
				+ (id != null ? "id=" + id + ", " : "")
				+ (ocorrencia != null ? "ocorrencia=" + ocorrencia.getId() + ", " : "")
				+ (data != null ? "data=" + data + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (operador != null ? "operador=" + operador + ", " : "")
				+ (anotacao != null ? "anotacao=" + anotacao : "") 
				+ "]";
	}
	
	public String toStringJasper() {
		return  (data != null ? "data=" + data + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (operador != null ? "operador=" + operador + ", " : "")
				+ (anotacao != null ? "anotacao=" + anotacao : "") ;
	}
}
