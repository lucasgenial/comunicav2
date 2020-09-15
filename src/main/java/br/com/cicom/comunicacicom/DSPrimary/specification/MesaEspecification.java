package br.com.cicom.comunicacicom.DSPrimary.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

public class MesaEspecification {
	
	
	public static Specification<Mesa> porEstabelecimento(Estabelecimento estabelecimento) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("estabelecimento"), estabelecimento);
		};
	}
	
	
}
