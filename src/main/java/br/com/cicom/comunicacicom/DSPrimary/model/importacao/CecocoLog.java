package br.com.cicom.comunicacicom.DSPrimary.model.importacao;

import java.time.LocalDateTime;

public class CecocoLog implements Importacao{

	private Long id;
	private Long numeroocorrencia;
	private Long estabelecimento;
	private LocalDateTime data;
	private String descricao;
	private String operador;
	private String anotacao;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEstabelecimento() {
		return estabelecimento;
	}
	public void setEstabelecimento(Long estabelecimento) {
		this.estabelecimento = estabelecimento;
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

	public CecocoLog(Long id, Long numeroocorrencia, Long estabelecimento, LocalDateTime data, String descricao,
			String operador, String anotacao) {
		super();
		this.id = id;
		this.numeroocorrencia = numeroocorrencia;
		this.estabelecimento = estabelecimento;
		this.data = data;
		this.descricao = descricao;
		this.operador = operador;
		this.anotacao = anotacao;
	}

	public CecocoLog() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anotacao == null) ? 0 : anotacao.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CecocoLog other = (CecocoLog) obj;
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
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CecocoLog [" + (id != null ? "id=" + id + ", " : "")
				+ (numeroocorrencia != null ? "numeroocorrencia=" + numeroocorrencia + ", " : "")
				+ (estabelecimento != null ? "estabelecimento=" + estabelecimento + ", " : "")
				+ (data != null ? "data=" + data + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (operador != null ? "operador=" + operador + ", " : "")
				+ (anotacao != null ? "anotacao=" + anotacao : "") + "]";
	}

	public Long getNumeroocorrencia() {
		return numeroocorrencia;
	}

	public void setNumeroocorrencia(Long numeroocorrencia) {
		this.numeroocorrencia = numeroocorrencia;
	}
	
}
