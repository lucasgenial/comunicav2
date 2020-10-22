package br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao;

import java.time.LocalDateTime;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;

public class NotificacaoDTO {

	private Long id;
	
	private MensagemDTO mensagem;
	
	private UsuarioDTO criador;
	
	private UsuarioDTO destinatario;
	
	private LocalDateTime dataLeitura;
	
	private LocalDateTime dataCriacao;
		
	public NotificacaoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MensagemDTO getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemDTO mensagem) {
		this.mensagem = mensagem;
	}

	public UsuarioDTO getCriador() {
		return criador;
	}

	public void setCriador(UsuarioDTO criador) {
		this.criador = criador;
	}

	public UsuarioDTO getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioDTO destinatario) {
		this.destinatario = destinatario;
	}

	public LocalDateTime getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(LocalDateTime dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criador == null) ? 0 : criador.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((dataLeitura == null) ? 0 : dataLeitura.hashCode());
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (criador == null) {
			if (other.criador != null)
				return false;
		} else if (!criador.equals(other.criador))
			return false;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (dataLeitura == null) {
			if (other.dataLeitura != null)
				return false;
		} else if (!dataLeitura.equals(other.dataLeitura))
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificacaoDTO [id=" + id + ", criador=" + criador.getServidor().getNome() + ", destinatario=" + destinatario.getServidor().getNome() + ", dataLeitura="
				+ dataLeitura + ", dataCriacao=" + dataCriacao + "]";
	}
	
}
