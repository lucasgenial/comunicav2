package br.com.cicom.comunicacicom.DSAudit.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Anderson Kroger
 */
@Entity
@Table(name = "AUDITORIA", schema = "comunicacicom2")
@SuppressWarnings("serial")
public class Auditoria implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATAHORA", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime datahora;

	//@NotNull
	//@OneToOne(targetEntity = Usuario.class)
	//@JoinColumn(name = "USUARIO")
	@Column(name = "USUARIO")
	private Long usuario;

	@NotBlank
	@Column(name = "HISTORICO", length = 2500)
	private String historico;

	public Auditoria() {
		super();
	}

	public Auditoria(Long id, LocalDateTime datahora, Long usuario, String historico) {
		super();
		this.id = id;
		this.datahora = datahora;
		this.usuario = usuario;
		this.historico = historico;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDatahora() {
		return datahora;
	}

	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datahora == null) ? 0 : datahora.hashCode());
		result = prime * result + ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Auditoria other = (Auditoria) obj;
		if (datahora == null) {
			if (other.datahora != null)
				return false;
		} else if (!datahora.equals(other.datahora))
			return false;
		if (historico == null) {
			if (other.historico != null)
				return false;
		} else if (!historico.equals(other.historico))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "Auditoria " 
				+ "[id=" + id 
				+ ", datahora=" + datahora 
				+ ", usuario=" + usuario 
				+ ", historico=" + historico
				+ "]";
	}
}