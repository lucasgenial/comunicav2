package br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;

public class MensagemDTO {
	
	private Long id;

	private String assunto;

	private String mensagem;
	
	@NotNull
	@OneToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CRIADOR")
	private Usuario criador;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATA_CRIACAO", nullable = false)
	private LocalDateTime dataCriacao;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
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
		return "MensagemDTO [id=" + id + ", assunto=" + assunto + ", mensagem=" + mensagem + "]";
	}
	
	
}
