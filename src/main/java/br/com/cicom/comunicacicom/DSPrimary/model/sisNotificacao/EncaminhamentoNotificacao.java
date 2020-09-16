package br.com.cicom.comunicacicom.DSPrimary.model.sisNotificacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ENCAMINHAMENTO_NOTIFICACAO")
@SuppressWarnings("serial")
public class EncaminhamentoNotificacao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@OneToOne(targetEntity = Notificacao.class, cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "NOTIFICACAO_ID")
	private Notificacao notificacao;

	@NotNull
	@OneToOne(targetEntity = Usuario.class, cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "USUARIO_ID")
	private Usuario usuario;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATA_LEITURA", nullable = false)
	private LocalDateTime dataLeitura;

	public EncaminhamentoNotificacao() {

	}

	public EncaminhamentoNotificacao(Long id, @NotNull Notificacao notificacao, @NotNull Usuario usuario,
			LocalDateTime dataLeitura) {
		super();
		this.id = id;
		this.notificacao = notificacao;
		this.usuario = usuario;
		this.dataLeitura = dataLeitura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		result = prime * result + ((notificacao == null) ? 0 : notificacao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		EncaminhamentoNotificacao other = (EncaminhamentoNotificacao) obj;
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
		if (notificacao == null) {
			if (other.notificacao != null)
				return false;
		} else if (!notificacao.equals(other.notificacao))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EncaminhamentoNotificacao [id=" + id + ", notificacao=" + notificacao + ", usuario=" + usuario
				+ ", dataLeitura=" + dataLeitura + "]";
	}
}
