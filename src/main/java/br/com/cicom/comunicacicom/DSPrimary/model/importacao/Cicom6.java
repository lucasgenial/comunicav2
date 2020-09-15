package br.com.cicom.comunicacicom.DSPrimary.model.importacao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Immutable
@Subselect("SELECT * FROM cicom06")
@SuppressWarnings("serial")
public class Cicom6 extends Cecoco implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NUMEROOCORRENCIA", unique = true)
	private Long id;

	@Column(name = "ESTABELECIMENTO")
	private Long estabelecimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATA_INICIO")
	private LocalDate datainicio;

	@DateTimeFormat(pattern = "HH:mm:ss")
	@Column(name = "HORA_INICIO")
	private LocalTime horainicio;

	@Column(name = "TIPO")
	private String tipo;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "HISTORICO")
	private String historico;

	@Column(name = "ENDERECO")
	private String endereco;

	@Column(name = "PONTOREFERENCIA")
	private String pontoreferencia;

	@Column(name = "BAIRRO")
	private String bairro;

	@Column(name = "DISTRITO")
	private String distrito;

	@Column(name = "CIDADE")
	private String cidade;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "ESTADO")
	private Long estado;

	public Cicom6() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Long estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public LocalDate getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(LocalDate datainicio) {
		this.datainicio = datainicio;
	}

	public LocalTime getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(LocalTime horainicio) {
		this.horainicio = horainicio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getPontoreferencia() {
		return pontoreferencia;
	}

	public void setPontoreferencia(String pontoreferencia) {
		this.pontoreferencia = pontoreferencia;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
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
		int result = super.hashCode();
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((datainicio == null) ? 0 : datainicio.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((distrito == null) ? 0 : distrito.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + ((horainicio == null) ? 0 : horainicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((pontoreferencia == null) ? 0 : pontoreferencia.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cicom6 other = (Cicom6) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (datainicio == null) {
			if (other.datainicio != null)
				return false;
		} else if (!datainicio.equals(other.datainicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (distrito == null) {
			if (other.distrito != null)
				return false;
		} else if (!distrito.equals(other.distrito))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (historico == null) {
			if (other.historico != null)
				return false;
		} else if (!historico.equals(other.historico))
			return false;
		if (horainicio == null) {
			if (other.horainicio != null)
				return false;
		} else if (!horainicio.equals(other.horainicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (pontoreferencia == null) {
			if (other.pontoreferencia != null)
				return false;
		} else if (!pontoreferencia.equals(other.pontoreferencia))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cicom6 [" + (id != null ? "id=" + id + ", " : "")
				+ (estabelecimento != null ? "estabelecimento=" + estabelecimento + ", " : "")
				+ (datainicio != null ? "datainicio=" + datainicio + ", " : "")
				+ (horainicio != null ? "horainicio=" + horainicio + ", " : "")
				+ (tipo != null ? "tipo=" + tipo + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (historico != null ? "historico=" + historico + ", " : "")
				+ (endereco != null ? "endereco=" + endereco + ", " : "")
				+ (pontoreferencia != null ? "pontoreferencia=" + pontoreferencia + ", " : "")
				+ (bairro != null ? "bairro=" + bairro + ", " : "")
				+ (distrito != null ? "distrito=" + distrito + ", " : "")
				+ (cidade != null ? "cidade=" + cidade + ", " : "")
				+ (latitude != null ? "latitude=" + latitude + ", " : "")
				+ (longitude != null ? "longitude=" + longitude + ", " : "")
				+ (estado != null ? "estado=" + estado + ", " : "") + "]";
	}

}