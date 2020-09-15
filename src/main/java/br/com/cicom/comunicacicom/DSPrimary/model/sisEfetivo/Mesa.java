package br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Unidade;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_MESA")
@SuppressWarnings("serial")
public class Mesa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@Basic
	@NotNull
	@Column(name = "NOME", unique = true)
	private String nome;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "INICIO_PLANTAO", nullable = false)
	private LocalDateTime inicioPlantao;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "FIM_PLANTAO", nullable = false)
	private LocalDateTime fimPlantao;

	@JsonIgnore
	@OneToMany(targetEntity = ServidorFuncaoInterno.class, cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinTable(name = "TBL_SERVIDORES_MESAS", joinColumns = { @JoinColumn(name = "MESAS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "SERVIDOR_ID") })
	private List<ServidorFuncaoInterno> servidores = new ArrayList<>();

	@JsonIgnore()
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(targetEntity = Estabelecimento.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "ESTABELECIMENTO_ID")
	private Estabelecimento estabelecimento;
	
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(targetEntity = Policiamento.class, orphanRemoval = false, mappedBy = "mesa", cascade = {
			CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH }, fetch = FetchType.LAZY)
	private Set<Policiamento> listaDePoliciamentos = new HashSet<>();

	@NotNull
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH }, targetEntity = Instituicao.class)
	@JoinTable(name = "TBL_MESA_INSTITUICAO", joinColumns = { @JoinColumn(name = "MESA_ID_FK") }, inverseJoinColumns = {
			@JoinColumn(name = "INSTITUICAO_ID_FK") })
	private Set<Instituicao> instituicoes;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH }, targetEntity = Unidade.class)
	@JoinTable(name = "TBL_MESA_UNIDADE", joinColumns = { @JoinColumn(name = "MESA_ID_FK") }, inverseJoinColumns = {
			@JoinColumn(name = "UNIDADE_ID_FK") })
	private Set<Unidade> unidades;

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;

	public Mesa() {
		this.ativo = true;
	}

	public Mesa(Long id, @NotNull String nome, @NotNull LocalDateTime inicioPlantao, @NotNull LocalDateTime fimPlantao,
			List<ServidorFuncaoInterno> servidores, Set<Policiamento> listaDePoliciamentos,
			Set<Instituicao> instituicoes, Set<Unidade> unidades, @NotNull boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.inicioPlantao = inicioPlantao;
		this.fimPlantao = fimPlantao;
		this.servidores = servidores;
		this.listaDePoliciamentos = listaDePoliciamentos;
		this.instituicoes = instituicoes;
		this.unidades = unidades;
		this.ativo = ativo;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
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

	public List<ServidorFuncaoInterno> getServidores() {
		return servidores;
	}

	public void setServidores(List<ServidorFuncaoInterno> servidores) {
		this.servidores = servidores;
	}

	public Set<Policiamento> getListaDePoliciamentos() {
		return listaDePoliciamentos;
	}

	public void setListaDePoliciamentos(Set<Policiamento> listaDePoliciamentos) {
		this.listaDePoliciamentos = listaDePoliciamentos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(Set<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public Set<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(Set<Unidade> unidades) {
		this.unidades = unidades;
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

	public List<ServidorFuncaoInterno> getServidoresTeledespacho() {
		List<ServidorFuncaoInterno> ListaDeTeledespacho = new ArrayList<>();

		for (ServidorFuncaoInterno servidor : this.servidores) {
			if (servidor.getFuncao().getNome().equals("OPERADOR DE TELEDESPACHO")) {
				ListaDeTeledespacho.add(servidor);
			}
		}
		return ListaDeTeledespacho;
	}

	public List<ServidorFuncaoInterno> getServidoresTeleAtendimento() {
		List<ServidorFuncaoInterno> ListaDeTeleAtendimento = new ArrayList<>();

		for (ServidorFuncaoInterno servidor : this.servidores) {
			if (servidor.getFuncao().getNome().equals("OPERADOR DE TELEATENDIMENTO")) {
				ListaDeTeleAtendimento.add(servidor);
			}
		}
		return ListaDeTeleAtendimento;
	}

	public List<ServidorFuncaoInterno> getServidoresVideoMonitoramento() {
		List<ServidorFuncaoInterno> ListaDeTeleVideoMonitoramento = new ArrayList<>();

		for (ServidorFuncaoInterno servidor : this.servidores) {
			if (servidor.getFuncao().getNome().equals("OPERADOR DE VÍDEO MONITORAMENTO")) {
				ListaDeTeleVideoMonitoramento.add(servidor);
			}
		}
		return ListaDeTeleVideoMonitoramento;
	}

	public List<ServidorFuncaoInterno> getServidoresCoordenadoresAdjuntos() {
		List<ServidorFuncaoInterno> ListaDeCoordenadoresAdjuntos = new ArrayList<>();

		for (ServidorFuncaoInterno servidor : this.servidores) {
			if (servidor.getFuncao().getNome().equals("COORDENADOR ADJUNTO - CICOM")) {
				ListaDeCoordenadoresAdjuntos.add(servidor);
			}
		}
		return ListaDeCoordenadoresAdjuntos;
	}

	public List<ServidorFuncaoInterno> getServidoresSuporteOperacional() {
		List<ServidorFuncaoInterno> ListaDeSuporteOperacional = new ArrayList<>();

		for (ServidorFuncaoInterno servidor : this.servidores) {
			if (servidor.getFuncao().getNome().equals("SUPORTE OPERACIONAL")) {
				ListaDeSuporteOperacional.add(servidor);
			}
		}
		return ListaDeSuporteOperacional;
	}

	public List<ServidorFuncaoInterno> getServidoresCoordenadores() {
		List<ServidorFuncaoInterno> ListaDeCoordenadores = new ArrayList<>();

		for (ServidorFuncaoInterno servidor : this.servidores) {
			if (servidor.getFuncao().getNome().equals("COORDENADOR - CICOM")) {
				ListaDeCoordenadores.add(servidor);
			}
		}
		return ListaDeCoordenadores;
	}
	
	public void atualizaIdsDosServidoresInternos(Mesa mesaDoBanco) {
					
		for(ServidorFuncaoInterno servidorDoBanco : mesaDoBanco.getServidores()) {
						
			for(ServidorFuncaoInterno servidorInternoAtual : this.getServidores()){
				if(servidorDoBanco.getServidor().getId().equals(servidorInternoAtual.getServidor().getId())) {
					servidorInternoAtual.setId(servidorDoBanco.getId());
				}
			}
		}
			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((fimPlantao == null) ? 0 : fimPlantao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicioPlantao == null) ? 0 : inicioPlantao.hashCode());
		result = prime * result + ((instituicoes == null) ? 0 : instituicoes.hashCode());
		result = prime * result + ((listaDePoliciamentos == null) ? 0 : listaDePoliciamentos.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((servidores == null) ? 0 : servidores.hashCode());
		result = prime * result + ((unidades == null) ? 0 : unidades.hashCode());
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
		Mesa other = (Mesa) obj;
		if (ativo != other.ativo)
			return false;
		if (fimPlantao == null) {
			if (other.fimPlantao != null)
				return false;
		} else if (!fimPlantao.equals(other.fimPlantao))
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
		if (instituicoes == null) {
			if (other.instituicoes != null)
				return false;
		} else if (!instituicoes.equals(other.instituicoes))
			return false;
		if (listaDePoliciamentos == null) {
			if (other.listaDePoliciamentos != null)
				return false;
		} else if (!listaDePoliciamentos.equals(other.listaDePoliciamentos))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (servidores == null) {
			if (other.servidores != null)
				return false;
		} else if (!servidores.equals(other.servidores))
			return false;
		if (unidades == null) {
			if (other.unidades != null)
				return false;
		} else if (!unidades.equals(other.unidades))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mesa [" 
				+ (id != null ? "id=" + id + ", " : "") 
				+ (nome != null ? "nome=" + nome + ", " : "")
				+ (estabelecimento != null ? "estabelecimento=" + estabelecimento + ", " : "") 
				+ (inicioPlantao != null ? "inicioPlantao=" + inicioPlantao + ", " : "")
				+ (fimPlantao != null ? "fimPlantao=" + fimPlantao + ", " : "")
				+ (instituicoes != null ? "instituicoes=" + instituicoes + ", " : "")
				+ (unidades != null ? "unidades=" + unidades + ", " : "") 
				+ "ativo=" + ativo + "]";
	}

}
