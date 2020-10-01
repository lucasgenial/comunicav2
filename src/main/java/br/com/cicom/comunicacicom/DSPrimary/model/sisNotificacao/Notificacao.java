package br.com.cicom.comunicacicom.DSPrimary.model.sisNotificacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;

@Entity
@Table(name = "NOTIFICACAO", schema = "comunicacicom")
@SuppressWarnings("serial")
public class Notificacao implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "ASSUNTO", unique = false)
	private String assunto;

	@NotNull
	@Column(name = "MENSAGEM", unique = false)
	private String mensagem;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATA_CRIACAO", nullable = false)
	private LocalDateTime dataCriacao;
	
	@NotNull
	@OneToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CRIADOR")
	private Usuario criador;
	
	public Notificacao() {
	}

	public Notificacao(Long id, @NotNull String assunto, @NotNull String mensagem, @NotNull LocalDateTime dataCriacao,
			@NotNull Usuario criador) {
		super();
		this.id = id;
		this.assunto = assunto;
		this.mensagem = mensagem;
		this.dataCriacao = dataCriacao;
		this.criador = criador;
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

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getCriador() {
		return criador;
	}

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((criador == null) ? 0 : criador.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
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
		Notificacao other = (Notificacao) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
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
		return "Notificacao [id=" + id + ", assunto=" + assunto + ", mensagem=" + mensagem + ", dataCriacao="
				+ dataCriacao + ", criador=" + criador + "]";
	}
}
