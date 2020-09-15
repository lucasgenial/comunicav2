package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Funcao;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_SERVIDOR_FUNCAO")
@SuppressWarnings("serial")
public class ServidorFuncao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "INICIO_PLANTAO", nullable = false)
	private LocalDateTime inicioPlantao;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "HORA_FINAL_PLANTAO", nullable = false)
	private LocalDateTime fimPlantao;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, targetEntity = ServidorExterno.class)
	@JoinColumn(name = "SERVIDOR")
	private ServidorExterno servidor;

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
	public ServidorFuncao() {
		this.setStatus(true);
	}

	public ServidorFuncao(ServidorExterno servidor, Funcao funcao) {
		this.servidor = servidor;
		this.funcao = funcao;
		this.setStatus(true);
	}

	public ServidorFuncao(Long id, @NotNull LocalDateTime inicioPlantao, @NotNull LocalDateTime fimPlantao,
			LocalTime pausa1, LocalTime pausa2, @NotNull ServidorExterno servidor, @NotNull Funcao funcao,
			@NotNull boolean ativo) {
		super();
		this.id = id;
		this.inicioPlantao = inicioPlantao;
		this.fimPlantao = fimPlantao;
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

	public ServidorExterno getServidor() {
		return servidor;
	}

	public void setServidor(ServidorExterno servidor) {
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
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.getId());
		hash = 97 * hash + Objects.hashCode(this.getInicioPlantao());
		hash = 97 * hash + Objects.hashCode(this.getFimPlantao());
		hash = 97 * hash + Objects.hashCode(this.isAtivo());
		hash = 97 * hash + Objects.hashCode(this.getServidor());
		hash = 97 * hash + Objects.hashCode(this.getFuncao());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ServidorFuncao)) {
			return false;
		}
		final ServidorFuncao other = (ServidorFuncao) obj;
		if (!Objects.equals(this.getId(), other.getId())) {
			return false;
		}
		if (!Objects.equals(this.getInicioPlantao(), other.getInicioPlantao())) {
			return false;
		}
		if (!Objects.equals(this.getFimPlantao(), other.getFimPlantao())) {
			return false;
		}
		if (!Objects.equals(this.isAtivo(), other.isAtivo())) {
			return false;
		}
		if (!Objects.equals(this.getServidor(), other.getServidor())) {
			return false;
		}
		if (!Objects.equals(this.getFuncao(), other.getFuncao())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ServidorFuncao [id=" + id + ", inicioPlantao=" + inicioPlantao + ", fimPlantao=" + fimPlantao
				+ ", servidor=" + servidor + ", funcao=" + funcao + ", ativo=" + ativo + "]";
	}

}
