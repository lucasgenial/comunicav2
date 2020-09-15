package br.com.cicom.comunicacicom.DSPrimary.POJO;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ClasseUtilitaria {

	public ValoresMensal buildValoresMensal(Long jan, Long fev, Long mar, Long abr, Long mai, Long jun, Long jul, 
			Long ago, Long set, Long out, Long nov, Long dez) {
        ValoresMensal valores = new ValoresMensal();

        if (jan != null) {
        	valores.setJaneiro(jan);
        }
        
        if (fev != null) {
        	valores.setFevereiro(fev);
        }
        
        if (mar != null) {
        	valores.setMarco(mar);
        }
        
        if (abr != null) {
        	valores.setAbril(abr);
        }
        
        if (mai != null) {
        	valores.setMaio(mai);
        }
        
        if (jun != null) {
        	valores.setJunho(jun);
        }
        
        if (jul != null) {
        	valores.setJulho(jul);
        }
        
        if (ago != null) {
        	valores.setAgosto(ago);
        }
        
        if (set != null) {
        	valores.setSetembro(set);
        }
        
        if (out != null) {
        	valores.setOutubro(out);
        }
        
        if (nov != null) {
        	valores.setNovembro(nov);
        }
        
        if (dez != null) {
        	valores.setDezembro(dez);
        }
        
        return valores;
    }


	public static List<Mes> geraMesesValidos(Year ano) {
		LocalDate dataHoje = LocalDate.now();
		List<Mes> meses = new ArrayList<>();
		if (ano.getValue() < dataHoje.getYear()) {

			for (int i = 01; i < 13; i++) {
				meses.add(new Mes(Month.of(i)));
			}

		} else {
			for (int i = 01; i < dataHoje.getMonthValue(); i++) {
				meses.add(new Mes(Month.of(i)));
			}
		}

		return meses;

	}

	public static List<Year> geraAnosValidos() {
		LocalDate dataHoje = LocalDate.now();
		List<Year> anos = new ArrayList<>();

		if (dataHoje.getMonth().getValue() > 1) {
			for (int i = 2018; i <= dataHoje.getYear(); i++) {
				anos.add(Year.of(i));
			}

		} else {
			for (int i = 2018; i < dataHoje.getYear(); i++) {
				anos.add(Year.of(i));
			}
		}

		return anos;
	}

	public static List<Year> geraAnosValidos(Year ano) {
		LocalDate dataHoje = LocalDate.now();
		List<Year> anos = new ArrayList<>();

		if (dataHoje.getYear() > ano.getValue()) {
			for (int i = ano.getValue(); i <= dataHoje.getYear(); i++) {
				anos.add(Year.of(i));
			}

		} else {
			anos.add(ano);
		}

		return anos;

	}

	public static List<Mes> geraMesesValidos(Year ano, Mes mes) {
		LocalDate dataHoje = LocalDate.now();
		List<Mes> meses = new ArrayList<>();

		if (ano.getValue() < dataHoje.getYear()) {

			if (mes.getValorInteiro() != 12) {
				for (int i = mes.getValorInteiro(); i < 13; i++) {
					meses.add(new Mes(Month.of(i)));
				}
			} else {
				meses.add(mes);
			}

		} else {
			for (int i = mes.getValorInteiro(); i < dataHoje.getMonthValue(); i++) {
				meses.add(new Mes(Month.of(i)));
			}
		}

		return meses;

	}
}
