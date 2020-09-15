package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

public class DadosPorCategoria {
	private String tipificacao;
	private Long sede = 0L;
	private Long zonaRural = 0L;
	private Long outras = 0L;
	private Long total = 0L;
	private Long idTipificacao;
	private Long janeiro = 0l;
	private Long fevereiro = 0l;
	private Long marco = 0l;
	private Long abril = 0l;
	private Long maio = 0l;
	private Long junho = 0l;
	private Long julho = 0l;
	private Long agosto = 0l;
	private Long setembro = 0l;
	private Long outubro = 0l;
	private Long novembro = 0l;
	private Long dezembro = 0l;
	
	public Long getValorDoMes(String mes) {
		switch (mes.toLowerCase()) {
		case "janeiro":
			{
				return janeiro;
			}
		case "fevereiro":
		{
			return fevereiro;
		}
		case "março":
		{
			return marco;
		}
		case "abril":
		{
			return abril;
		}
		case "maio":
		{
			return maio;
		}
		case "junho":
		{
			return junho;
		}
		case "julho":
		{
			return julho;
		}
		case "agosto":
		{
			return agosto;
		}
		case "setembro":
		{
			return setembro;
		}
		case "outubro":
		{
			return outubro;
		}
		case "novembro":
		{
			return novembro;
		}
		case "dezembro":
		{
			return dezembro;
		}

		default:
			break;
		}
		
		
		return 0L;
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
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public DadosPorCategoria(String tipificacao,Long idTipificacao, Long sede, Long zonaRural, Long outras, Long total) {
		this.tipificacao = tipificacao;
		this.idTipificacao = idTipificacao;
		this.sede = sede;
		this.zonaRural = zonaRural;
		this.outras = outras;
		this.total = total;
	}

	public String getTipificacao() {
		return tipificacao;
	}

	public void setTipificacao(String tipificacao) {
		this.tipificacao = tipificacao;
	}

	public Long getSede() {
		return sede;
	}

	public void setSede(Long sede) {
		this.sede = sede;
	}

	public Long getZonaRural() {
		return zonaRural;
	}

	public void setZonaRural(Long zonaRural) {
		this.zonaRural = zonaRural;
	}

	public Long getOutras() {
		return outras;
	}

	public void setOutras(Long outras) {
		this.outras = outras;
	}
	
	public Long getIdTipificacao() {
		return idTipificacao;
	}

	public void setIdTipificacao(Long idTipificacao) {
		this.idTipificacao = idTipificacao;
	}
	@Override
	public String toString() {
		return "Dados [ sede=" + sede + ", zonaRural=" + zonaRural + ", outras=" + outras + ", total=" + total + "]";
	}

}
