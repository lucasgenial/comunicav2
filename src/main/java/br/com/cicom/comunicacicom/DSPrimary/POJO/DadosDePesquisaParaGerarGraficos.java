package br.com.cicom.comunicacicom.DSPrimary.POJO;

import java.util.ArrayList;
import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;

public class DadosDePesquisaParaGerarGraficos {
	private Integer ano;
	private Integer mesInicial;
	private Integer mesFinal;
	private String tipoGrafico;
	private Long idEstabelecimento;
	private List<Cidade> idCidade = new ArrayList<>();
	private Long idLocalidade;
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Integer getMesInicial() {
		return mesInicial;
	}
	public void setMesInicial(Integer mesInicial) {
		this.mesInicial = mesInicial;
	}
	public Integer getMesFinal() {
		return mesFinal;
	}
	public void setMesFinal(Integer mesFinal) {
		this.mesFinal = mesFinal;
	}
	public String getTipoGrafico() {
		return tipoGrafico;
	}
	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}
	public Long getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(Long idEstebelecimento) {
		this.idEstabelecimento = idEstebelecimento;
	}
	public List<Cidade> getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(List<Cidade> idCidade) {
		this.idCidade = idCidade;
	}
	public Long getIdLocalidade() {
		return idLocalidade;
	}
	public void setIdLocalidade(Long idLocalidade) {
		this.idLocalidade = idLocalidade;
	}
	@Override
	public String toString() {
		return "DadosDePesquisaParaGerarGraficos [ano=" + ano + ", mesInicial=" + mesInicial + ", mesFinal=" + mesFinal
				+ ", tipoGrafico=" + tipoGrafico + ", idEstabelecimento=" + idEstabelecimento + ", idCidade=" + idCidade
				+ ", idLocalidade=" + idLocalidade + "]";
	}	
}
