package br.com.cicom.comunicacicom.DSPrimary.specification;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.RegistroOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;


public class OcorrenciaSpecification {

	public static Specification<Ocorrencia> quandoDoAno(Integer ano) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("dataOcorrencia").get("year").as(Integer.class), ano);
		};
	}
		
	public static Specification<Ocorrencia> byEstabelecimento(Estabelecimento estabelecimento) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("estabelecimento"), estabelecimento);
		};
	}
	
	public static Specification<Ocorrencia> quandoNaoTemRegistro() {
		return (root, query, cb) ->
		{	
			Subquery<RegistroOcorrencia> subquery = query.subquery(RegistroOcorrencia.class);
			Root<RegistroOcorrencia> subqueryRoot = subquery.from(RegistroOcorrencia.class);
			subquery.select(subqueryRoot);
		         
			subquery.where(cb.equal(root.get("id"), subqueryRoot.get("ocorrencia")));
	
		    return cb.not(cb.exists(subquery));
		};
	}
	
	public static Specification<Ocorrencia> quandoEhCvli(List<Tipificacao> tipificacoes) {
		return (root, query, cb) ->
		{	
			return root.get("tipificacao").in(tipificacoes);
		};
	}
	
	public static Specification<Ocorrencia> quandoEntreDatas(final LocalDateTime dataInicio,final LocalDateTime dataFim) {
		
		return (root, query, cb) ->
		{		
		    return cb.between(root.get("dataOcorrencia").as(LocalDateTime.class), dataInicio, dataFim);
		};
	}
	
	public static Specification<Ocorrencia> dosUltimos3Dias() {
		
		return (root, query, cb) ->
		{		
		    return cb.between(root.get("dataOcorrencia").as(LocalDateTime.class), LocalDateTime.now().minusDays(3), LocalDateTime.now());
		};
	}

}
