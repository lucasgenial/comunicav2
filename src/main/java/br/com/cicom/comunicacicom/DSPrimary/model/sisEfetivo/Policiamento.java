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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Unidade;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "TBL_POLICIAMENTO")
@SuppressWarnings("serial")
public class Policiamento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@Basic
	@Column(name = "BCS")
	private boolean BCS;

	@Basic
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "INICIO_PLANTAO")
	private LocalDateTime comecoPlantao;

	@Basic
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "FIM_PLANTAO")
	private LocalDateTime terminoPlantao;

	@Basic
	@Column(name = "BAIXADO")
	private boolean baixado;

	@Basic
	@Column(name = "HR_BAIXA")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horaBaixa;

	@NotNull
	@ManyToOne(targetEntity = Mesa.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MESA")
	private Mesa mesa;

	@OneToOne(targetEntity = Unidade.class)
	@JoinColumn(name = "UNIDADE")
	private Unidade unidade;

	@NotNull
	@OneToOne(targetEntity = TipoServico.class)
	@JoinColumn(name = "TIPO_SERVICO")
	private TipoServico tipoServico;

	@NotNull
	@OneToOne(targetEntity = Modalidade.class)
	@JoinColumn(name = "MODALIDADE_FK")
	private Modalidade modalidade;

	@Column(name = "PREFIXO")
	private String prefixo;

	@Column(name = "PLACA_POLICIAL")
	private String placaPolicial;

	@Column(name = "KM_INICIAL")
	private String kmInicial;

	@Column(name = "KM_FINAL")
	private String kmFinal;

	@NotNull
	@OneToOne(targetEntity = Cidade.class)
	@JoinColumn(name = "CIDADE")
	private Cidade cidade;
    
	@OneToOne(targetEntity = Localidade.class)
	@JoinColumn(name = "LOCALIDADE")
	private Localidade localidade;

	@OneToOne(targetEntity = Bairro.class)
	@JoinColumn(name = "BAIRRO")
	private Bairro bairro;

	@Column(name = "TELEFONE")
	private String telefone;

	@NotNull
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE,	CascadeType.PERSIST }, targetEntity = ServidorFuncao.class)
	@JoinTable(name = "TBL_GUARNICAO", uniqueConstraints = @UniqueConstraint(columnNames = { "POLICIAMENTO_ID_FK", "SERVIDOR_ID_FK" }),
	joinColumns = { @JoinColumn(name = "POLICIAMENTO_ID_FK") },
	inverseJoinColumns = { @JoinColumn(name = "SERVIDOR_ID_FK") })
	private List<ServidorFuncao> guarnicao = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = Recurso.class)
	@JoinTable(name = "TBL_POLICIAMENTO_RECURSOS", uniqueConstraints = @UniqueConstraint(columnNames = {
			"POLICIAMENTO_ID", "RECURSO_ID" }), joinColumns = {
					@JoinColumn(name = "POLICIAMENTO_ID") }, inverseJoinColumns = { @JoinColumn(name = "RECURSO_ID") })
	private Set<Recurso> recursos = new HashSet<>();

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;

	/*
	 * CONSTRUTORES
	 */
	public Policiamento() {
		this.setStatus(true);
		this.setBaixado(false);
	}

	public Policiamento(Long id, boolean bCS, @NotNull LocalDateTime comecoPlantao,
			@NotNull LocalDateTime terminoPlantao, boolean baixado, LocalDateTime horaBaixa, @NotNull Mesa mesa,
			Unidade unidade, @NotNull TipoServico tipoServico, @NotNull Modalidade modalidade, String prefixo,
			String placaPolicial, String kmInicial, String kmFinal, @NotNull Cidade cidade, Localidade localidade,
			Bairro bairro, @NotNull List<ServidorFuncao> guarnicao, Set<Recurso> recursos, @NotNull boolean ativo) {
		super();
		this.id = id;
		BCS = bCS;
		this.comecoPlantao = comecoPlantao;
		this.terminoPlantao = terminoPlantao;
		this.baixado = baixado;
		this.horaBaixa = horaBaixa;
		this.mesa = mesa;
		this.unidade = unidade;
		this.tipoServico = tipoServico;
		this.modalidade = modalidade;
		this.prefixo = prefixo;
		this.placaPolicial = placaPolicial;
		this.kmInicial = kmInicial;
		this.kmFinal = kmFinal;
		this.cidade = cidade;
		this.localidade = localidade;
		this.bairro = bairro;
		this.guarnicao = guarnicao;
		this.recursos = recursos;
		this.ativo = ativo;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getBCS() {
		return BCS;
	}

	public void setBCS(boolean bCS) {
		BCS = bCS;
	}

	public boolean isBaixado() {
		return baixado;
	}

	public void setBaixado(boolean baixado) {
		this.baixado = baixado;
	}

	public LocalDateTime getHoraBaixa() {
		return horaBaixa;
	}

	public void setHoraBaixa(LocalDateTime horaBaixa) {
		this.horaBaixa = horaBaixa;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public String[] getDadosEstatisticos() {
		
		String string[] = {"1",Integer.toString(this.getGuarnicao().size()),this.getCidade().getNome(),this.getUnidade().getInstituicao().getNome(),this.getModalidade().getNome()};

		return  string;
	}
		
	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	@JsonProperty("abrangencia")
	public String getAbrangencia() {
		String abrangencia = "";

		if (getCidade() != null) {
			if (this.getCidade().getNome().toCharArray().length < 12) {
				abrangencia = this.getCidade().getNome();
			} else if (this.getCidade().getNome().toCharArray().length >= 12) {
				char caracteres[] = this.getCidade().getNome().toCharArray();

				for (int i = 0; i < 10; i++) {
					abrangencia = abrangencia + caracteres[i];
				}

			}
		}

		if (getLocalidade() != null) {
			if (this.getLocalidade().getNome().toCharArray().length < 12) {
				abrangencia = abrangencia + " - " + this.getLocalidade().getNome();

			} else if (this.getBairro() != null && this.getLocalidade().getNome().toCharArray().length >= 12) {
				char caracteres[] = this.getLocalidade().getNome().toCharArray();
				abrangencia = abrangencia + " - ";
				for (int i = 0; i < 10; i++) {
					abrangencia = abrangencia + caracteres[i];
				}
			}

		}
		if (this.getBairro() != null) {
			if (this.getBairro().getNome().toCharArray().length < 12) {
				abrangencia = abrangencia + " - " + this.getBairro().getNome();

			} else if (this.getBairro() != null && this.getBairro().getNome().toCharArray().length >= 12) {
				char caracteres[] = this.getBairro().getNome().toCharArray();
				abrangencia = abrangencia + " - ";
				for (int i = 0; i < 10; i++) {
					abrangencia = abrangencia + caracteres[i];
				}
			}
		}

		return abrangencia;
	}

	public List<ServidorFuncao> getGuarnicao() {
		return guarnicao;
	}

	public void setGuarnicao(List<ServidorFuncao> guarnicao) {
		this.guarnicao = guarnicao;
	}

	public Set<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
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

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public String getPlacaPolicial() {
		return placaPolicial;
	}

	public void setPlacaPolicial(String placaPolicial) {
		this.placaPolicial = placaPolicial;
	}

	public String getKmInicial() {
		return kmInicial;
	}

	public void setKmInicial(String kmInicial) {
		this.kmInicial = kmInicial;
	}

	public String getKmFinal() {
		return kmFinal;
	}

	public void setKmFinal(String kmFinal) {
		this.kmFinal = kmFinal;
	}

	public LocalDateTime getComecoPlantao() {
		return comecoPlantao;
	}

	public void setComecoPlantao(LocalDateTime comecoPlantao) {
		this.comecoPlantao = comecoPlantao;
	}

	public LocalDateTime getTerminoPlantao() {
		return terminoPlantao;
	}

	public void setTerminoPlantao(LocalDateTime terminoPlantao) {
		this.terminoPlantao = terminoPlantao;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void atualizaIdsDosServidoresDaGuarnicao(Policiamento policiamentoDoBanco) {

		for (ServidorFuncao servidorDoBanco : policiamentoDoBanco.getGuarnicao()) {

			for (ServidorFuncao servidorInternoAtual : this.getGuarnicao()) {
				if (servidorDoBanco.getServidor().getMatricula()
						.equals(servidorInternoAtual.getServidor().getMatricula())) {
					servidorInternoAtual.setId(servidorDoBanco.getId());
				}
			}
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (BCS ? 1231 : 1237);
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + (baixado ? 1231 : 1237);
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((comecoPlantao == null) ? 0 : comecoPlantao.hashCode());
		result = prime * result + ((horaBaixa == null) ? 0 : horaBaixa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((placaPolicial == null) ? 0 : placaPolicial.hashCode());
		result = prime * result + ((prefixo == null) ? 0 : prefixo.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
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
		Policiamento other = (Policiamento) obj;
		if (BCS != other.BCS)
			return false;
		if (ativo != other.ativo)
			return false;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (baixado != other.baixado)
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (comecoPlantao == null) {
			if (other.comecoPlantao != null)
				return false;
		} else if (!comecoPlantao.equals(other.comecoPlantao))
			return false;
		if (horaBaixa == null) {
			if (other.horaBaixa != null)
				return false;
		} else if (!horaBaixa.equals(other.horaBaixa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mesa == null) {
			if (other.mesa != null)
				return false;
		} else if (!mesa.equals(other.mesa))
			return false;
		if (modalidade == null) {
			if (other.modalidade != null)
				return false;
		} else if (!modalidade.equals(other.modalidade))
			return false;
		if (placaPolicial == null) {
			if (other.placaPolicial != null)
				return false;
		} else if (!placaPolicial.equals(other.placaPolicial))
			return false;
		if (prefixo == null) {
			if (other.prefixo != null)
				return false;
		} else if (!prefixo.equals(other.prefixo))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Policiamento [" + (id != null ? "id=" + id + ", " : "") + "BCS=" + BCS + ", "
				+ (comecoPlantao != null ? "comecoPlantao=" + comecoPlantao + ", " : "")
				+ (terminoPlantao != null ? "terminoPlantao=" + terminoPlantao + ", " : "") + "baixado=" + baixado
				+ ", " + (horaBaixa != null ? "horaBaixa=" + horaBaixa + ", " : "")
				+ (mesa != null ? "mesa=" + mesa + ", " : "") + (unidade != null ? "unidade=" + unidade + ", " : "")
				+ (tipoServico != null ? "tipoServico=" + tipoServico + ", " : "")
				+ (modalidade != null ? "modalidade=" + modalidade + ", " : "")
				+ (prefixo != null ? "prefixo=" + prefixo + ", " : "")
				+ (placaPolicial != null ? "placaPolicial=" + placaPolicial + ", " : "")
				+ (kmInicial != null ? "kmInicial=" + kmInicial + ", " : "")
				+ (kmFinal != null ? "kmFinal=" + kmFinal + ", " : "")
				+ (cidade != null ? "cidade=" + cidade + ", " : "")
				+ (localidade != null ? "localidade=" + localidade + ", " : "")
				+ (bairro != null ? "bairro=" + bairro + ", " : "")
				+ (telefone != null ? "telefone=" + telefone + ", " : "")
				+ (guarnicao != null ? "guarnicao=" + guarnicao + ", " : "")
				+ (recursos != null ? "recursos=" + recursos + ", " : "") + "ativo=" + ativo + "]";
	}
	

	/*
	 * @Override public String toString() { return "Policiamento [id=" + id +
	 * ", BCS=" + BCS + ", comecoPlantao=" + comecoPlantao + ", terminoPlantao=" +
	 * terminoPlantao + ", baixado=" + baixado + ", horaBaixa=" + horaBaixa +
	 * ", mesa=" + mesa.getNome() + ", unidade=" + unidade.getNome() +
	 * ", tipoServico=" + tipoServico.getNome() + ", modalidade=" +
	 * modalidade.getNome() + ", prefixo=" + prefixo + ", placaPolicial=" +
	 * placaPolicial + ", kmInicial=" + kmInicial + ", kmFinal=" + kmFinal +
	 * ", cidade=" + cidade.getNome() + ", localidade=" + localidade.getNome() +
	 * ", bairro=" + bairro.getNome() + ", telefone=" + telefone + ", guarnicao=" +
	 * guarnicao.size() + ", recursos=" + recursos.size() + ", ativo=" + ativo +
	 * "]"; }
	 */
}
