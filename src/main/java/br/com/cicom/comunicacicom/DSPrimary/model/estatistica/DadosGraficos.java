package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

public class DadosGraficos {
	
	private LocalDateTime dataInicial;
	private LocalDateTime dataFinal;
	private List<Tipificacao> listaTipificacao = new ArrayList<>();
	private List<Estabelecimento> estabelecimentos = new ArrayList<>();
	private List<Cidade> listaCidades = new ArrayList<>();
	private List<Localidade> listaLocalidades = new ArrayList<>();
	private List<Bairro> listaBairros = new ArrayList<>();
	private String tipoGrafico;
	private String titulo;
	
	public DadosGraficos(LocalDateTime dataInicial, LocalDateTime dataFinal, List<Tipificacao> listaTipificacao,
			List<Estabelecimento> estabelecimentos, List<Cidade> listaCidades, List<Localidade> listaLocalidades,
			List<Bairro> listaBairros, String tipoGrafico, String titulo) {
		super();
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.listaTipificacao = listaTipificacao;
		this.estabelecimentos = estabelecimentos;
		this.listaCidades = listaCidades;
		this.listaLocalidades = listaLocalidades;
		this.listaBairros = listaBairros;
		this.tipoGrafico = tipoGrafico;
		this.titulo = titulo;
	}
	
	public DadosGraficos() {
	}

	public LocalDateTime getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDateTime dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Tipificacao> getListaTipificacao() {
		return listaTipificacao;
	}

	public void setListaTipificacao(List<Tipificacao> listaTipificacao) {
		this.listaTipificacao = listaTipificacao;
	}

	public List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public List<Localidade> getListaLocalidades() {
		return listaLocalidades;
	}

	public void setListaLocalidades(List<Localidade> listaLocalidades) {
		this.listaLocalidades = listaLocalidades;
	}

	public List<Bairro> getListaBairros() {
		return listaBairros;
	}

	public void setListaBairros(List<Bairro> listaBairros) {
		this.listaBairros = listaBairros;
	}

	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + ((estabelecimentos == null) ? 0 : estabelecimentos.hashCode());
		result = prime * result + ((listaBairros == null) ? 0 : listaBairros.hashCode());
		result = prime * result + ((listaCidades == null) ? 0 : listaCidades.hashCode());
		result = prime * result + ((listaLocalidades == null) ? 0 : listaLocalidades.hashCode());
		result = prime * result + ((listaTipificacao == null) ? 0 : listaTipificacao.hashCode());
		result = prime * result + ((tipoGrafico == null) ? 0 : tipoGrafico.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		DadosGraficos other = (DadosGraficos) obj;
		if (dataFinal == null) {
			if (other.dataFinal != null)
				return false;
		} else if (!dataFinal.equals(other.dataFinal))
			return false;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (estabelecimentos == null) {
			if (other.estabelecimentos != null)
				return false;
		} else if (!estabelecimentos.equals(other.estabelecimentos))
			return false;
		if (listaBairros == null) {
			if (other.listaBairros != null)
				return false;
		} else if (!listaBairros.equals(other.listaBairros))
			return false;
		if (listaCidades == null) {
			if (other.listaCidades != null)
				return false;
		} else if (!listaCidades.equals(other.listaCidades))
			return false;
		if (listaLocalidades == null) {
			if (other.listaLocalidades != null)
				return false;
		} else if (!listaLocalidades.equals(other.listaLocalidades))
			return false;
		if (listaTipificacao == null) {
			if (other.listaTipificacao != null)
				return false;
		} else if (!listaTipificacao.equals(other.listaTipificacao))
			return false;
		if (tipoGrafico == null) {
			if (other.tipoGrafico != null)
				return false;
		} else if (!tipoGrafico.equals(other.tipoGrafico))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DadosGraficos [dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", listaTipificacao="
				+ listaTipificacao.size() + ", estabelecimentos=" + estabelecimentos.size() + ", listaCidades=" + listaCidades.size()
				+ ", listaLocalidades=" + listaLocalidades.size() + ", listaBairros=" + listaBairros.size() + ", tipoGrafico="
				+ tipoGrafico + ", titulo=" + titulo + "]";
	}
}
