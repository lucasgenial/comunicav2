package br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class MensagemDTO {
	
	private Long id;

	@NotNull
	@Size(min = 2, max = 120)
	private String assunto;

	@NotNull
	@Size(min = 2, max = 10000)
	private String mensagem;
	
	@NotNull
	private Long emissor;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCriacao;
	
	@NotEmpty
	private List<Long> destinatarios = new ArrayList<>();
	
	public MensagemDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getEmissor() {
		return emissor;
	}

	public void setEmissor(Long emissor) {
		this.emissor = emissor;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<Long> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<Long> destinatarios) {
		this.destinatarios = destinatarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((destinatarios == null) ? 0 : destinatarios.hashCode());
		result = prime * result + ((emissor == null) ? 0 : emissor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
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
		MensagemDTO other = (MensagemDTO) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (destinatarios == null) {
			if (other.destinatarios != null)
				return false;
		} else if (!destinatarios.equals(other.destinatarios))
			return false;
		if (emissor == null) {
			if (other.emissor != null)
				return false;
		} else if (!emissor.equals(other.emissor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MensagemDTO [id=" + id + ", assunto=" + assunto + ", mensagem=" + mensagem + ", emissor=" + emissor
				+ ", dataCriacao=" + dataCriacao + ", destinatarios=" + destinatarios + "]";
	}
	
}
