package br.com.cicom.comunicacicom.DSPrimary.model.gestao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

/**
 * @author Anderson Kroger
 */
@Entity
@Table(name = "VISITA")
@SuppressWarnings("serial")
public class Visita implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	@NotNull
	@Column(name = "EMPRESA", unique = false)
	private String empresa;

	@Nullable
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
	@OneToOne(targetEntity = Servico.class, fetch = FetchType.LAZY)
	@JoinTable(name = "VISITA_SERVICO", joinColumns = { @JoinColumn(name = "ID_VISITA") }, inverseJoinColumns = {
	@JoinColumn(name = "ID_SERVICO") })
	private Servico servico = new Servico();

	@ManyToMany(targetEntity=Visitante.class, cascade = {CascadeType.MERGE})
	@JoinTable(name = "VISITA_VISITANTE", joinColumns = {
			@JoinColumn(name = "ID_VISITA")}, inverseJoinColumns = {
			@JoinColumn(name = "ID_VISITANTE") })
	private List<Visitante> visitantes = new ArrayList<>();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss", iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "INICIO_SERVICO")
	private LocalDateTime inicioServico;
	

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss", iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "FIM_SERVICO")
	private LocalDateTime fimServico;

	@NotNull
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
	@OneToOne(targetEntity = Estabelecimento.class)
	@JoinColumn(name = "ESTABELECIMENTO_ID")
	private Estabelecimento estabelecimento;

	@NotNull
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
	@OneToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "USUARIO")
	private Usuario usuario;

	@NotBlank
	@Column(name = "HISTORICO", length = 2500)
	private String historico;

	public Visita() {
		super();
	}

	public List<Visitante> getVisitantes() {
		return visitantes;
	}

	public void setVisitantes(List<Visitante> visitantes) {
		this.visitantes = visitantes;
	}

	public Visita(Long id, @NotNull String empresa, Servico servico,
			LocalDateTime inicioServico, LocalDateTime fimServico,
			@NotNull Estabelecimento estabelecimento, @NotNull Usuario usuario, @NotBlank String historico) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.servico = servico;
		this.inicioServico = inicioServico;
		this.fimServico = fimServico;
		this.estabelecimento = estabelecimento;
		this.usuario = usuario;
		this.historico = historico;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getInicioServico() {
		return inicioServico;
	}

	public void setInicioServico(LocalDateTime inicioServico) {
		this.inicioServico = inicioServico;
	}

	public LocalDateTime getFimServico() {
		return fimServico;
	}

	public void setFimServico(LocalDateTime fimServico) {
		this.fimServico = fimServico;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico.toUpperCase();
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa.toUpperCase();
	}

	public Long getServicoId() {
		return servico.getId();
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((fimServico == null) ? 0 : fimServico.hashCode());
		result = prime * result + ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicioServico == null) ? 0 : inicioServico.hashCode());
		result = prime * result + ((servico == null) ? 0 : servico.hashCode());
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
		Visita other = (Visita) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (fimServico == null) {
			if (other.fimServico != null)
				return false;
		} else if (!fimServico.equals(other.fimServico))
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
		if (inicioServico == null) {
			if (other.inicioServico != null)
				return false;
		} else if (!inicioServico.equals(other.inicioServico))
			return false;
		if (servico == null) {
			if (other.servico != null)
				return false;
		} else if (!servico.equals(other.servico))
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
		return "Visita [id=" + id + ", empresa=" + empresa + ", servico=" + servico + ", visitantes=" + visitantes
				+ ", inicioServico=" + inicioServico + ", fimServico=" + fimServico + ", estabelecimento="
				+ estabelecimento + ", usuario=" + usuario + ", historico=" + historico + "]";
	}
	
}
