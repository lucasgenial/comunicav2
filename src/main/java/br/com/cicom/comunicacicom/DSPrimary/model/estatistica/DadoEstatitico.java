package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadoEstatitico {
	

	String tipificacao;
	Long regiaoSede = 0l;
	Long regiaoRural = 0l;
	Long regiaoDemaisCidades = 0l;
	Long ano = 0l;
	Long janeiro = 0l;
	Long fevereiro = 0l;
	Long marco = 0l;
	Long abril = 0l;
	Long maio = 0l;
	Long junho = 0l;
	Long julho = 0l;
	Long agosto = 0l;
	Long setembro = 0l;
	Long outubro = 0l;
	Long novembro = 0l;
	Long dezembro = 0l;
	//Long total;
	
	
	List<String> mes = new ArrayList<>(Arrays.asList("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
			"Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"));

	public DadoEstatitico() {
	}
	
	public DadoEstatitico(String tipificacao, Long janeiro, Long fevereiro, Long marco, Long abril, Long maio,
			Long junho, Long julho, Long agosto, Long setembro, Long outubro, Long novembro, Long dezembro) {
		
		this.tipificacao = tipificacao;
		this.janeiro = janeiro;
		this.fevereiro = fevereiro;
		this.marco = marco;
		this.abril = abril;
		this.maio = maio;
		this.junho = junho;
		this.julho = julho;
		this.agosto = agosto;
		this.setembro = setembro;
		this.outubro = outubro;
		this.novembro = novembro;
		this.dezembro = dezembro;
	}
	
	public DadoEstatitico(String tipificacao, Long janeiro, Long fevereiro, Long marco, Long abril, Long maio,
			Long junho, Long julho, Long agosto, Long setembro, Long outubro, Long novembro, Long dezembro, Long sede) {
		super();
		this.tipificacao = tipificacao;
		this.janeiro = janeiro;
		this.fevereiro = fevereiro;
		this.marco = marco;
		this.abril = abril;
		this.maio = maio;
		this.junho = junho;
		this.julho = julho;
		this.agosto = agosto;
		this.setembro = setembro;
		this.outubro = outubro;
		this.novembro = novembro;
		this.dezembro = dezembro;
		this.regiaoSede = sede;
		//this.total = total;
	}
	
	
	public List<String> getMes() {
		return mes;
	}

	public void setMes(List<String> mes) {
		this.mes = mes;
	}

	
	public String getTipificacao() {
		return tipificacao;
	}

	public void setTipificacao(String tipificacao) {
		this.tipificacao = tipificacao;
	}

	public Long getJaneiro() {
		return janeiro;
	}

	public void setJaneiro(Long janeiro) {
		this.janeiro = janeiro;
	}

	public Long getFevereiro() {
		return fevereiro;
	}

	public Long getRegiaoSede() {
		return regiaoSede;
	}

	public void setRegiaoSede(Long regiaoSede) {
		this.regiaoSede = regiaoSede;
	}

	public Long getRegiaoRural() {
		return regiaoRural;
	}

	public void setRegiaoRural(Long regiaoRural) {
		this.regiaoRural = regiaoRural;
	}

	public Long getRegiaoDemaisCidades() {
		return regiaoDemaisCidades;
	}

	public void setRegiaoDemaisCidades(Long regiaoDemaisCidades) {
		this.regiaoDemaisCidades = regiaoDemaisCidades;
	}

	public void setFevereiro(Long fevereiro) {
		this.fevereiro = fevereiro;
	}

	public Long getMarco() {
		return marco;
	}

	public void setMarco(Long marco) {
		this.marco = marco;
	}

	public Long getAbril() {
		return abril;
	}

	public void setAbril(Long abril) {
		this.abril = abril;
	}

	public Long getMaio() {
		return maio;
	}

	public void setMaio(Long maio) {
		this.maio = maio;
	}

	public Long getJunho() {
		return junho;
	}

	public void setJunho(Long junho) {
		this.junho = junho;
	}

	public Long getJulho() {
		return julho;
	}

	public void setJulho(Long julho) {
		this.julho = julho;
	}

	public Long getAgosto() {
		return agosto;
	}

	public void setAgosto(Long agosto) {
		this.agosto = agosto;
	}

	public Long getSetembro() {
		return setembro;
	}

	public void setSetembro(Long setembro) {
		this.setembro = setembro;
	}

	public Long getOutubro() {
		return outubro;
	}

	public void setOutubro(Long outubro) {
		this.outubro = outubro;
	}

	public Long getNovembro() {
		return novembro;
	}

	public void setNovembro(Long novembro) {
		this.novembro = novembro;
	}

	public Long getDezembro() {
		return dezembro;
	}

	public void setDezembro(Long dezembro) {
		this.dezembro = dezembro;
	}

	public Long getTotal() {
	
		return this.janeiro+this.fevereiro+this.marco+this.abril+this.maio+this.junho+this.julho+this.agosto+this.setembro+this.outubro+this.novembro+this.dezembro;
	}

	//public void setTotal(Long total) {
	//	this.total = total;
	//}
	
	@Override
	public String toString() {
		return "DadoEstatitico [tipificacao=" + tipificacao + ", janeiro=" + janeiro + ", fevereiro=" + fevereiro
				+ ", marco=" + marco + ", abril=" + abril + ", maio=" + maio + ", junho=" + junho + ", julho=" + julho
				+ ", agosto=" + agosto + ", setembro=" + setembro + ", outubro=" + outubro + ", novembro=" + novembro
				+ ", dezembro=" + dezembro + ", total=" + this.getTotal() + "]";
	
	
	}
}
