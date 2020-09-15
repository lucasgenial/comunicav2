package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Funcao;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_SERVIDOR_FUNCAO_INTERNO")
@SuppressWarnings("serial")
public class ServidorFuncaoInterno implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "INICIO_PLANTAO", nullable = false)
	private LocalDateTime inicioPlantao;

	@JsonIgnore
	@ManyToOne(targetEntity = Mesa.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MESA")
	private Mesa mesa;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "HORA_FINAL_PLANTAO", nullable = false)
	private LocalDateTime fimPlantao;

	@DateTimeFormat(pattern = "HH:mm:ss")
	@Column(name = "HORA_PAUSA_1", nullable = true)
	private LocalTime pausa1;

	@DateTimeFormat(pattern = "HH:mm:ss")
	@Column(name = "HORA_PAUSA_2", nullable = true)
	private LocalTime pausa2;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Servidor.class)
	@JoinColumn(name = "SERVIDOR")
	private Servidor servidor;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Funcao.class)
	@JoinColumn(name = "FUNCAO")
	private Funcao funcao;

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;

	/*
	 * CONSTRUTORES
	 */
	public ServidorFuncaoInterno() {
		this.setStatus(true);
	}

	public ServidorFuncaoInterno(Servidor servidor, Funcao funcao) {
		this.servidor = servidor;
		this.funcao = funcao;
		this.setStatus(true);
	}

	public ServidorFuncaoInterno(Long id, @NotNull LocalDateTime inicioPlantao, Mesa mesa,
			@NotNull LocalDateTime fimPlantao, @NotNull LocalTime pausa1, @NotNull LocalTime pausa2,
			@NotNull Servidor servidor, @NotNull Funcao funcao, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.inicioPlantao = inicioPlantao;
		this.mesa = mesa;
		this.fimPlantao = fimPlantao;
		this.pausa1 = pausa1;
		this.pausa2 = pausa2;
		this.servidor = servidor;
		this.funcao = funcao;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getInicioPlantao() {
		return inicioPlantao;
	}

	public void setInicioPlantao(LocalDateTime inicioPlantao) {
		this.inicioPlantao = inicioPlantao;
	}

	public LocalDateTime getFimPlantao() {
		return fimPlantao;
	}

	public void setFimPlantao(LocalDateTime fimPlantao) {
		this.fimPlantao = fimPlantao;
	}

	public LocalTime getPausa1() {
		return pausa1;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public void setPausa1(LocalTime pausa1) {
		this.pausa1 = pausa1;
	}

	public LocalTime getPausa2() {
		return pausa2;
	}

	public void setPausa2(LocalTime pausa2) {
		this.pausa2 = pausa2;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setStatus(boolean ativo) {
		this.setAtivo(ativo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((fimPlantao == null) ? 0 : fimPlantao.hashCode());
		result = prime * result + ((funcao == null) ? 0 : funcao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicioPlantao == null) ? 0 : inicioPlantao.hashCode());
		result = prime * result + ((mesa == null) ? 0 : mesa.hashCode());
		result = prime * result + ((pausa1 == null) ? 0 : pausa1.hashCode());
		result = prime * result + ((pausa2 == null) ? 0 : pausa2.hashCode());
		result = prime * result + ((servidor == null) ? 0 : servidor.hashCode());
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
		ServidorFuncaoInterno other = (ServidorFuncaoInterno) obj;
		if (ativo != other.ativo)
			return false;
		if (fimPlantao == null) {
			if (other.fimPlantao != null)
				return false;
		} else if (!fimPlantao.equals(other.fimPlantao))
			return false;
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicioPlantao == null) {
			if (other.inicioPlantao != null)
				return false;
		} else if (!inicioPlantao.equals(other.inicioPlantao))
			return false;
		if (mesa == null) {
			if (other.mesa != null)
				return false;
		} else if (!mesa.equals(other.mesa))
			return false;
		if (pausa1 == null) {
			if (other.pausa1 != null)
				return false;
		} else if (!pausa1.equals(other.pausa1))
			return false;
		if (pausa2 == null) {
			if (other.pausa2 != null)
				return false;
		} else if (!pausa2.equals(other.pausa2))
			return false;
		if (servidor == null) {
			if (other.servidor != null)
				return false;
		} else if (!servidor.equals(other.servidor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServidorFuncaoInterno [" + (id != null ? "id=" + id + ", " : "")
				+ (inicioPlantao != null ? "inicioPlantao=" + inicioPlantao + ", " : "")
				+ (fimPlantao != null ? "fimPlantao=" + fimPlantao + ", " : "")
				+ (pausa1 != null ? "pausa1=" + pausa1 + ", " : "") + (pausa2 != null ? "pausa2=" + pausa2 + ", " : "")
				+ (servidor != null ? "servidor=" + servidor + ", " : "")
				+ (funcao != null ? "funcao=" + funcao + ", " : "") + "ativo=" + ativo + "]";
	}

}
