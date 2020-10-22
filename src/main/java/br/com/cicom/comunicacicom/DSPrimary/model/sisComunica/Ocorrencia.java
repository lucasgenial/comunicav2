package br.com.cicom.comunicacicom.DSPrimary.model.sisComunica;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cicom.comunicacicom.DSPrimary.model.Formatador;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.RegistroOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Endereco;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

/**
 *
 * @author Lucas Matos
 */
@Entity
@Table(name = "OCORRENCIA", uniqueConstraints = { @UniqueConstraint(columnNames = { "SIC", "ESTABELECIMENTO_ID" }) })
@SuppressWarnings("serial")
public class Ocorrencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotBlank
	@Column(name = "SIC", nullable = false)
	private String sic;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATA_OCORRENCIA", nullable = false)
	private LocalDateTime dataOcorrencia;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATA_CRIACAO", nullable = false)
	private LocalDateTime dataDaCriacao;

	@NotNull
	@OneToOne(targetEntity = Estabelecimento.class, cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "ESTABELECIMENTO_ID")
	private Estabelecimento estabelecimento;

	@NotNull
	@OneToOne(targetEntity = Tipificacao.class)
	@JoinColumn(name = "TIPIFICACAO")
	private Tipificacao tipificacao;

	@NotNull
	@OneToOne(targetEntity = Endereco.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "ENDERECO", nullable = false)
	private Endereco endereco;

	@NotBlank
	@Size(min = 0, max = 100)
	@Column(name = "GUARNICAO")
	private String guarnicao;

	@NotBlank
	@Size(min = 10, max = 2500)
	@Column(name = "DESCRICAO", length = 2500)
	private String descricao;

	@NotBlank
	@Size(min = 10, max = 2500)
	@Column(name = "HISTORICO", length = 2500)
	private String historico;

	@NotNull
	@OneToOne(targetEntity = EstadoOcorrencia.class)
	@JoinColumn(name = "ESTADO_OCORRENCIA")
	private EstadoOcorrencia estadoOcorrencia;

	@NotNull
	@OneToOne(targetEntity = Servidor.class)
	@JoinColumn(name = "SERVIDOR")
	private Servidor servidor;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DATA_MODIFICACAO")
	private LocalDateTime dataUltimaModificao;

	@NotNull
	@Column(name = "ENVIADA")
	private boolean enviada;

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;
	
	@OneToMany(targetEntity = OcorrenciaLog.class, mappedBy = "ocorrencia", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<OcorrenciaLog> ocorrencialog = new ArrayList<>();
	
	@OneToOne(mappedBy = "ocorrencia",targetEntity = RegistroOcorrencia.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private RegistroOcorrencia registroOcorrencia;

	public Ocorrencia() {
		this.ativo = true;
	}
	
	public Ocorrencia(Long id, @NotBlank String sic, @NotNull LocalDateTime dataOcorrencia,
				@NotNull LocalTime horaOcorrencia, @NotNull LocalDateTime dataDaCriacao,
				@NotNull Estabelecimento estabelecimento, @NotNull Tipificacao tipificacao, @NotNull Endereco endereco,
				@NotBlank @Size(min = 0, max = 100) String guarnicao,
				@NotBlank @Size(min = 10, max = 2500) String descricao,
				@NotBlank @Size(min = 10, max = 2500) String historico, @NotNull EstadoOcorrencia estadoOcorrencia,
				@NotNull Servidor servidor, @NotNull LocalDateTime dataUltimaModificao, @NotNull boolean enviada,
				@NotNull boolean ativo, List<OcorrenciaLog> ocorrencialog) {
		super();
		this.id = id;
		this.sic = sic;
		this.dataOcorrencia = dataOcorrencia;
		this.dataDaCriacao = dataDaCriacao;
		this.estabelecimento = estabelecimento;
		this.tipificacao = tipificacao;
		this.endereco = endereco;
		this.guarnicao = guarnicao;
		this.descricao = descricao;
		this.historico = historico;
		this.estadoOcorrencia = estadoOcorrencia;
		this.servidor = servidor;
		this.dataUltimaModificao = dataUltimaModificao;
		this.enviada = enviada;
		this.ativo = true;
		this.ocorrencialog = ocorrencialog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSic() {
		return sic;
	}

	public void setSic(String sic) {
		this.sic = sic;
	}

	public LocalDateTime getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public LocalDateTime getDataDaCriacao() {
		return dataDaCriacao;
	}

	public void setDataDaCriacao(LocalDateTime dataDaCriacao) {
		this.dataDaCriacao = dataDaCriacao;
	}
	
	
	public RegistroOcorrencia getRegistroOcorrencia() {
		return registroOcorrencia;
	}

	public void setRegistroOcorrencia(RegistroOcorrencia registroOcorrencia) {
		this.registroOcorrencia = registroOcorrencia;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Tipificacao getTipificacao() {
		return tipificacao;
	}

	public void setTipificacao(Tipificacao tipificacao) {
		this.tipificacao = tipificacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getGuarnicao() {
		return guarnicao;
	}

	public void setGuarnicao(String guarnicao) {
		this.guarnicao = guarnicao;
	}

	public String getDescricao() {
		return descricao;
	}

	@JsonIgnore
	public String getDescricaoFormatado() {
		return new Formatador().limpaFormatacao(this.getDescricao());
	}

	@JsonIgnore
	public String getHistoricoFormatado() {
		return new Formatador().limpaFormatacao(this.getHistorico());
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EstadoOcorrencia getEstadoOcorrencia() {
		return estadoOcorrencia;
	}

	public void setEstadoOcorrencia(EstadoOcorrencia estadoOcorrencia) {
		this.estadoOcorrencia = estadoOcorrencia;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public LocalDateTime getDataUltimaModificao() {
		return dataUltimaModificao;
	}

	public void setDataUltimaModificao(LocalDateTime dataUltimaModificao) {
		this.dataUltimaModificao = dataUltimaModificao;
	}

	public boolean isEnviada() {
		return enviada;
	}

	public void setEnviada(boolean enviada) {
		this.enviada = enviada;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	private String getFormatacaoDeCampo(int quantidade, String nome) {
		String espacosInicio = "";
		String espacosFim = "";

		if (nome.length() > quantidade) {
			char caracteres[] = nome.toCharArray();
			String string = "";

			for (int i = 0; i < quantidade; i++) {

				string = string + caracteres[i];
			}

			for (int i = string.toCharArray().length - 1; i >= 0 && string.toCharArray()[i] != ' '; i--) {
				string.toCharArray()[i] = '@';
			}

			nome = "";

			for (int i = 0; i < string.toCharArray().length; i++) {
				nome = nome + string.toCharArray()[i];

			}

			nome = nome.replaceAll("@", "");
		}

		nome = nome.replaceAll(" ", "@");

		if (nome.length() < quantidade && ((quantidade - nome.length()) % 2 == 0)) {

			for (int i = 0; i < (quantidade - nome.length()) / 2; i++) {
				espacosInicio = espacosInicio + " ";
				espacosFim = espacosFim + " ";
			}

		} else if (nome.length() < quantidade && ((quantidade - nome.length()) % 2 != 0)) {

			for (int i = 0; i < (quantidade - (nome.length() + 1)) / 2; i++) {
				espacosInicio = espacosInicio + " ";
			}
			espacosFim = espacosInicio + " ";
		}

		return espacosInicio + nome + espacosFim;
	}

	public String getToStringModificado() {
		String tostring;

		DateTimeFormatter dformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		tostring = this.getFormatacaoDeCampo(10, this.sic)
				+ this.getFormatacaoDeCampo(20,
						this.getDataOcorrencia().format(dformat) )
				+ this.getFormatacaoDeCampo(35, this.tipificacao.getNome())
				+ this.getFormatacaoDeCampo(45, this.getDescricao())
				+ this.getFormatacaoDeCampo(50,
						this.getEndereco().getCidade().getNome() + " / "
								+ this.getEndereco().getBairro().getLocalidade().getNome() + " / "
								+ this.getEndereco().getBairro().getNome());

		tostring = tostring.replaceAll(" ", "&nbsp;");
		tostring = tostring.replaceAll("@", "&nbsp;");
		return tostring;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((dataDaCriacao == null) ? 0 : dataDaCriacao.hashCode());
		result = prime * result + ((dataOcorrencia == null) ? 0 : dataOcorrencia.hashCode());
		result = prime * result + ((dataUltimaModificao == null) ? 0 : dataUltimaModificao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + (enviada ? 1231 : 1237);
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((estadoOcorrencia == null) ? 0 : estadoOcorrencia.hashCode());
		result = prime * result + ((guarnicao == null) ? 0 : guarnicao.hashCode());
		result = prime * result + ((historico == null) ? 0 : historico.hashCode());
//		result = prime * result + ((horaOcorrencia == null) ? 0 : horaOcorrencia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ocorrencialog == null) ? 0 : ocorrencialog.hashCode());
		result = prime * result + ((servidor == null) ? 0 : servidor.hashCode());
		result = prime * result + ((sic == null) ? 0 : sic.hashCode());
		result = prime * result + ((tipificacao == null) ? 0 : tipificacao.hashCode());
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
		Ocorrencia other = (Ocorrencia) obj;
		if (ativo != other.ativo)
			return false;
		if (dataDaCriacao == null) {
			if (other.dataDaCriacao != null)
				return false;
		} else if (!dataDaCriacao.equals(other.dataDaCriacao))
			return false;
		if (dataOcorrencia == null) {
			if (other.dataOcorrencia != null)
				return false;
		} else if (!dataOcorrencia.equals(other.dataOcorrencia))
			return false;
		if (dataUltimaModificao == null) {
			if (other.dataUltimaModificao != null)
				return false;
		} else if (!dataUltimaModificao.equals(other.dataUltimaModificao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (enviada != other.enviada)
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (estadoOcorrencia == null) {
			if (other.estadoOcorrencia != null)
				return false;
		} else if (!estadoOcorrencia.equals(other.estadoOcorrencia))
			return false;
		if (guarnicao == null) {
			if (other.guarnicao != null)
				return false;
		} else if (!guarnicao.equals(other.guarnicao))
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
		if (ocorrencialog == null) {
			if (other.ocorrencialog != null)
				return false;
		} else if (!ocorrencialog.equals(other.ocorrencialog))
			return false;
		if (servidor == null) {
			if (other.servidor != null)
				return false;
		} else if (!servidor.equals(other.servidor))
			return false;
		if (sic == null) {
			if (other.sic != null)
				return false;
		} else if (!sic.equals(other.sic))
			return false;
		if (tipificacao == null) {
			if (other.tipificacao != null)
				return false;
		} else if (!tipificacao.equals(other.tipificacao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ocorrencia [id=" + id + ", sic=" + sic + ", dataOcorrencia=" + dataOcorrencia + ", horaOcorrencia="
				+ dataDaCriacao + ", estabelecimento=" + estabelecimento
				+ ", tipificacao=" + tipificacao + ", endereco=" + endereco + ", guarnicao=" + guarnicao
				+ ", descricao=" + descricao + ", historico=" + historico + ", estadoOcorrencia=" + estadoOcorrencia
				+ ", servidor=" + servidor + ", enviada=" + enviada
				+ ", ativo=" + ativo + ", ocorrencialog=" + ocorrencialog + "]";
	}

	public List<OcorrenciaLog> getOcorrencialog() {
		return ocorrencialog;
	}

	public void setOcorrencialog(List<OcorrenciaLog> ocorrencialog) {
		this.ocorrencialog = ocorrencialog;
	}

}