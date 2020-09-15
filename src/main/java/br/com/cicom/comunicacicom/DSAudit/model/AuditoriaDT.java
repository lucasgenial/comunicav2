package br.com.cicom.comunicacicom.DSAudit.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

@Immutable
@Entity
@Table(name = "view_auditoria" , schema = "comunicacicom2")
public class AuditoriaDT implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true)
	private Long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime datahora;
	private String historico;
	private Long usuario;
	private String login;

	public AuditoriaDT(Long id, LocalDateTime datahora, Long usuario, String historico , String login) {
		super();
		this.id = id;
		this.datahora = datahora;
		this.usuario = usuario;
		this.historico = historico;
		this.login = login;
	}

	public AuditoriaDT() {
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDatahora() {
		return datahora;
	}

	public String getHistorico() {
		return historico;
	}

	public Long getUsuario() {
		return usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "AuditoriaDT [" 
				+ (id != null ? "id=" + id + ", " : "" )
				+ (datahora != null ? "datahora=" + datahora  + ", " : "")
				+ (historico != null ? "historico=" + historico + ", " : "")
				+ (usuario != null ? "usuario=" + usuario + ", " : "")
				+ (login != null ? "login=" + login  : "" )
				+ "]";
	}
}
