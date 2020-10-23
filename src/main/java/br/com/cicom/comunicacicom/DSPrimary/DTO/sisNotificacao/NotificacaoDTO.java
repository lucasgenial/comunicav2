package br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao;

import java.time.LocalDateTime;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;

public class NotificacaoDTO {

	private Long id;
	
	private UsuarioDTO receptor;
	
	private LocalDateTime dataLeitura;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioDTO getReceptor() {
		return receptor;
	}

	public void setReceptor(UsuarioDTO receptor) {
		this.receptor = receptor;
	}

	public LocalDateTime getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(LocalDateTime dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataLeitura == null) ? 0 : dataLeitura.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((receptor == null) ? 0 : receptor.hashCode());
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
		NotificacaoDTO other = (NotificacaoDTO) obj;
		if (dataLeitura == null) {
			if (other.dataLeitura != null)
				return false;
		} else if (!dataLeitura.equals(other.dataLeitura))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (receptor == null) {
			if (other.receptor != null)
				return false;
		} else if (!receptor.equals(other.receptor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificacaoDTO [id=" + id + ", receptor=" + receptor + ", dataLeitura=" + dataLeitura + "]";
	}
	
}
