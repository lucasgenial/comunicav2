package br.com.cicom.comunicacicom.DSPrimary.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visita;
import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

public class VisitaEspecification {
	
	public static Specification<Visita> porEstabelecimento(Estabelecimento estabelecimento) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("estabelecimento"), estabelecimento);
		};
	}
	
	public static Specification<Visita> quandoContemOVisitante(Visitante visitante) {
		return (root, query, cb) ->
		{			
		  Subquery<Visitante> subquery = query.subquery(Visitante.class);
		  Root<Visitante> visit = subquery.from(Visitante.class);
		  subquery.select(visit).where(cb.equal(visit.get("cpf"),visitante.getCpf()));
		 return  subquery.in(root.join("visitantes"));
		};
	}	
	
	public static Specification<Visita> quandoAtivo() {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("ativo"), true);
		};
	}	
	
	public static Specification<Visita> filtro(Visita visita, Estabelecimento estabelecimento) {
		return (root, query, cb) ->
		{
			List<Predicate> predicates = new ArrayList<>();
		
			predicates.add(cb.equal(root.get("estabelecimento"), visita.getEstabelecimento()));
			
			if (visita.getEmpresa() != null && !visita.getEmpresa().equals("")) {
				predicates.add(cb.like(root.get("empresa"), visita.getEmpresa()));
			}
			
			if (visita.getServico() != null) {
				predicates.add(cb.equal(root.get("servico").get("id"),  visita.getServico().getId()));
			}

			if (visita.getInicioServico() != null && visita.getFimServico() != null) {
				predicates.add(cb.between(root.get("inicioServico").as(LocalDateTime.class), visita.getInicioServico(), visita.getFimServico()));
			}
			
			if (visita.getInicioServico() != null && visita.getFimServico() == null) {
				predicates.add(cb.between(root.get("inicioServico").as(LocalDateTime.class), visita.getInicioServico(), LocalDateTime.now().minusMonths(6)));
			}
			
			if (visita.getInicioServico() == null && visita.getFimServico() != null) {
				predicates.add(cb.between(root.get("inicioServico").as(LocalDateTime.class), LocalDateTime.now().minusMonths(6) , visita.getFimServico()));
			}

			if (visita.getInicioServico() == null && visita.getFimServico() == null) {
				predicates.add(cb.between(root.get("inicioServico").as(LocalDateTime.class), LocalDateTime.now().minusMonths(6), LocalDateTime.now()));
			}
			
			if (visita.getHistorico() != null && !visita.getHistorico().equals("")) {
				predicates.add(cb.like(root.get("historico"), "%"+visita.getHistorico()+"%"));
			}
			
			
		    if(visita.getVisitantes().get(0).getCpf() != null && !visita.getVisitantes().get(0).getCpf().equals("")){
			 
			  Subquery<Visitante> subquery = query.subquery(Visitante.class);
			  Root<Visitante> visit = subquery.from(Visitante.class);
			  					  
			  subquery.select(visit).where(cb.equal(visit.get("cpf"),visita.getVisitantes().get(0).getCpf()));
  
				  predicates.add(subquery.in(root.join("visitantes")));
		    } 			
			  
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));		
		}; 
	}

}	
