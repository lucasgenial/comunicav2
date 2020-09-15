package br.com.cicom.comunicacicom.DSPrimary.POJO;

import org.springframework.beans.factory.annotation.Value;


public interface CategoriaPOJO {
	
	String getCategoria();
	
	@Value("#{@classeUtilitaria.buildValoresMensal(target.janeiro, target.fevereiro, target.marco, target.abril, target.maio, target.junho, "
			+ "target.julho, target.agosto, target.setembro, target.outubro, target.novembro, target.dezembro)}")
	ValoresMensal getValores();
	
	Long getTotal();
}