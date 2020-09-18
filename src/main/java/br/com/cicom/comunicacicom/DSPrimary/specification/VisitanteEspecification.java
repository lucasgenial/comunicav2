package br.com.cicom.comunicacicom.DSPrimary.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;

public class VisitanteEspecification {	
	
	public static Specification<Visitante> filtro(Visitante visitante) {
		return (root, query, cb) ->
		{
			List<Predicate> predicates = new ArrayList<>();
		
	
			if (visitante.getNome() != null && !visitante.getNome().equals("")) {
				predicates.add(cb.like(root.get("nome"), "%"+visitante.getNome()+"%"));
			}
			
			if (visitante.getCpf() != null && !visitante.getCpf().equals("")) {
				predicates.add(cb.equal(root.get("cpf"), visitante.getCpf()));
			}
	  
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));		
		}; 
	}

}	
