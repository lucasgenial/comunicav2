package br.com.cicom.comunicacicom.DSPrimary.POJO;

import java.time.Month;

public class Mes {
	private Month mes;
	private int valorInteiro;
	private String nome;

	public Mes(Month mes) {
		this.setMes(mes);
	}

	public Month getMes() {
		return mes;
	}

	public void setMes(Month mes) {
		this.mes = mes;
	}

	public int getValorInteiro() {
		return mes.getValue();
	}

	public void setValorInteiro(int valorInteiro) {
		this.valorInteiro = valorInteiro;
		mes = Month.of(valorInteiro);
	}

	public static String getNome(int mes) {
		switch (mes) {

		case 1: {
			return "JANEIRO";
		}
		case 2: {
			return "FEVEREIRO";
		}
		case 3: {
			return "MARÇO";
		}
		case 4: {
			return "ABRIL";
		}
		case 5: {
			return "MAIO";
		}
		case 6: {
			return "JUNHO";
		}
		case 7: {
			return "JULHO";
		}
		case 8: {
			return "AGOSTO";
		}
		case 9: {
			return "SETEMBRO";
		}
		case 10: {
			return "OUTUBRO";
		}
		case 11: {
			return "NOVEMBRO";
		}
		case 12: {
			return "DEZEMBRO";
		}

		}

		return "Mês indefinido";
	}

	public String getNome() {
		switch (mes.toString()) {

		case "JANUARY": {
			return "JANEIRO";
		}
		case "FEBRUARY": {
			return "FEVEREIRO";
		}
		case "MARCH": {
			return "MARÇO";
		}
		case "APRIL": {
			return "ABRIL";
		}
		case "MAY": {
			return "MAIO";
		}
		case "JUNE": {
			return "JUNHO";
		}
		case "JULY": {
			return "JULHO";
		}
		case "AUGUST": {
			return "AGOSTO";
		}
		case "SEPTEMBER": {
			return "SETEMBRO";
		}
		case "OCTOBER": {
			return "OUTUBRO";
		}
		case "NOVEMBER": {
			return "NOVEMBRO";
		}
		case "DECEMBER": {
			return "DEZEMBRO";
		}

		}

		return nome;
	}

}
