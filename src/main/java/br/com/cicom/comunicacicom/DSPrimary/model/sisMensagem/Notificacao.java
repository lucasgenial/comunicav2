package br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem;

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
@Table(name = "NOTIFICACAO")
@SuppressWarnings("serial")
public class Notificacao implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;	
		
	@NotNull
	@OneToOne(targetEntity = Mensagem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "mensagem")
	private Mensagem mensagem;
	
	@NotNull
	@OneToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "destinatario")
	private Usuario destinatario;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "data_leitura", nullable = false)
	private LocalDateTime dataLeitura;
	
	public Notificacao() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
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
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
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
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notificacao [id=" + id + ", mensagem=" + mensagem.getId() +  ", destinatario=" + destinatario.getServidor().getNome() + ", dataLeitura=" + dataLeitura + "]";
	}
}
