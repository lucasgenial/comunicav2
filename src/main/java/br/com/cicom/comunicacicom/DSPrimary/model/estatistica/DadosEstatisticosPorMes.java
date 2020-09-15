package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.util.ArrayList;
import java.util.List;

public class DadosEstatisticosPorMes {

	List<DadosPorCategoria> dados = new ArrayList<>();
	private String mes;
	private String tipificacaoGeral;
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<DadosPorCategoria> getDados() {
		return dados;
	}

	public Long getTotalDoMes() {

		Long total = 0L;
		for (DadosPorCategoria dado : this.getDados()) {
			total += dado.getTotal();
		}

		return total;
	}

	public Long getTotalSede() {

		Long total = 0L;
		for (DadosPorCategoria dado : this.getDados()) {
			total += dado.getSede();
		}

		return total;
	}

	public Long getTotalZonaRural() {

		Long total = 0L;
		for (DadosPorCategoria dado : this.getDados()) {
			total += dado.getZonaRural();
		}

		return total;
	}

	public Long getTotalOutras() {

		Long total = 0L;
		for (DadosPorCategoria dado : this.getDados()) {
			total += dado.getOutras();
		}

		return total;
	}

	public static DadosPorCategoria criaDados(String tipificacao,Long id, Long sede, Long zonaRural, Long outras, Long total) {
		return new DadosPorCategoria(tipificacao,id, sede, zonaRural, outras, total);
	}

	public void setDados(List<DadosPorCategoria> dados) {
		this.dados = dados;
	}

	public String getTipificacaoGeral() {
		return tipificacaoGeral;
	}

	public void setTipificacaoGeral(String tipificacaoGeral) {
		this.tipificacaoGeral = tipificacaoGeral;
	}

}
