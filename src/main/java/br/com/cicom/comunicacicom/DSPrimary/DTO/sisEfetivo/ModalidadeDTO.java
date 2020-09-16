package br.com.cicom.comunicacicom.DSPrimary.DTO.sisEfetivo;

public class ModalidadeDTO {
	private String nomeModalidade;
	private Long idModalidade;
	private Long qtdModalidade;
	private Long qtdPoliciamento;

	public ModalidadeDTO() {

	}

	public ModalidadeDTO(String nomeModalidade, Long id, Long qtdModalidade, Long qtdPoliciamento) {
		this.nomeModalidade = nomeModalidade;
		this.idModalidade = id;
		this.qtdModalidade = qtdModalidade;
		this.qtdPoliciamento = qtdPoliciamento;
	}

	public String getNomeModalidade() {
		return nomeModalidade;
	}

	public void setNomeModalidade(String nomeModalidade) {
		this.nomeModalidade = nomeModalidade;
	}

	public Long getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(Long id) {
		this.idModalidade = id;
	}

	public Long getQtdModalidade() {
		return qtdModalidade;
	}

	public void setQtdModalidade(Long qtdModalidade) {
		this.qtdModalidade = qtdModalidade;
	}

	public Long getQtdPoliciamento() {
		return qtdPoliciamento;
	}

	public void setQtdPoliciamento(Long qtdPoliciamento) {
		this.qtdPoliciamento = qtdPoliciamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idModalidade == null) ? 0 : idModalidade.hashCode());
		result = prime * result + ((nomeModalidade == null) ? 0 : nomeModalidade.hashCode());
		result = prime * result + ((qtdModalidade == null) ? 0 : qtdModalidade.hashCode());
		result = prime * result + ((qtdPoliciamento == null) ? 0 : qtdPoliciamento.hashCode());
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
		ModalidadeDTO other = (ModalidadeDTO) obj;
		if (idModalidade == null) {
			if (other.idModalidade != null)
				return false;
		} else if (!idModalidade.equals(other.idModalidade))
			return false;
		if (nomeModalidade == null) {
			if (other.nomeModalidade != null)
				return false;
		} else if (!nomeModalidade.equals(other.nomeModalidade))
			return false;
		if (qtdModalidade == null) {
			if (other.qtdModalidade != null)
				return false;
		} else if (!qtdModalidade.equals(other.qtdModalidade))
			return false;
		if (qtdPoliciamento == null) {
			if (other.qtdPoliciamento != null)
				return false;
		} else if (!qtdPoliciamento.equals(other.qtdPoliciamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModalidadeDTO [nomeModalidade=" + nomeModalidade + ", idModalidade=" + idModalidade + ", qtdModalidade=" + qtdModalidade
				+ ", qtdPoliciamento=" + qtdPoliciamento + "]";
	}
}
