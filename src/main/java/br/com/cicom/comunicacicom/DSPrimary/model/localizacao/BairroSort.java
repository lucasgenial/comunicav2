package br.com.cicom.comunicacicom.DSPrimary.model.localizacao;

import java.util.Comparator;

public class BairroSort implements Comparator<Bairro> {
	
	@Override
	public int compare(Bairro a, Bairro b) {
		return a.getNome().toUpperCase().compareTo(b.getNome().toUpperCase());
	}
}
