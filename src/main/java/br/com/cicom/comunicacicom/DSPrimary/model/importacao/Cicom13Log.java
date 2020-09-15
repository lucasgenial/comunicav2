package br.com.cicom.comunicacicom.DSPrimary.model.importacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Immutable
@Subselect("SELECT * FROM cicom13log")
@SuppressWarnings("serial")
public class Cicom13Log extends CecocoLog implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NUMEROOCORRENCIA")
    private Long numeroocorrencia;

	@Column(name = "ESTABELECIMENTO")
	private Long estabelecimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATAHORA")
	private LocalDateTime data;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "OPERADOR")
	private String operador;

	@Column(name = "ANOTACAO")
	private String anotacao;


	public Cicom13Log(Long id, Long ocorrencia, Long estabelecimento, LocalDateTime data, String descricao,
			String operador, String anotacao) {
		super();
		this.id = id;
		this.numeroocorrencia = ocorrencia;
		this.estabelecimento = estabelecimento;
		this.data = data;
		this.descricao = descricao;
		this.operador = operador;
		this.anotacao = anotacao;
	}

	public Cicom13Log() {
	}

	public Long getId() {
		return id;
	}

	public Long getNumeroocorrencia() {
		return numeroocorrencia;
	}

	public Long getEstabelecimento() {
		return estabelecimento;
	}

	public LocalDateTime getData() {
		return data;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getOperador() {
		return operador;
	}

	public String getAnotacao() {
		return anotacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((anotacao == null) ? 0 : anotacao.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numeroocorrencia == null) ? 0 : numeroocorrencia.hashCode());
		result = prime * result + ((operador == null) ? 0 : operador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cicom13Log other = (Cicom13Log) obj;
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
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numeroocorrencia == null) {
			if (other.numeroocorrencia != null)
				return false;
		} else if (!numeroocorrencia.equals(other.numeroocorrencia))
			return false;
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cicom6Log [" + (id != null ? "id=" + id + ", " : "")
				+ (numeroocorrencia != null ? "ocorrencia=" + numeroocorrencia + ", " : "")
				+ (estabelecimento != null ? "estabelecimento=" + estabelecimento + ", " : "")
				+ (data != null ? "data=" + data + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (operador != null ? "operador=" + operador + ", " : "")
				+ (anotacao != null ? "anotacao=" + anotacao : "") + "]";
	}

}
