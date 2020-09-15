package br.com.cicom.comunicacicom.DSPrimary.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

public class PoliciamentoEspecification {
	
	
	public static Specification<Policiamento> porEstabelecimento(Estabelecimento estabelecimento) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("mesa").get("estabelecimento"), estabelecimento);
		};
	}
	
	public static Specification<Policiamento> quandoAtivo() {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("ativo"), true);
		};
	}
	
	public static Specification<Policiamento> porCidade(Cidade cidade) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("cidade"), cidade);
		};
	}
	
	public static Specification<Policiamento> porLocalidade(Localidade localidade) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("localidade"), localidade);
		};
	}
	
	public static Specification<Policiamento> porBairro(Bairro bairro) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("bairro"), bairro);
		};
	}
	
	public static Specification<Policiamento> porMesa(Mesa mesa) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("mesa"), mesa);
		};
	}
	
	public static Specification<Policiamento> porIdDaMesa(Long idMesa) {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("mesa").get("id"), idMesa);
		};
	}  
	
	public static Specification<Policiamento> porDataDeCriacao(LocalDateTime data) {
		return (root, query, cb) ->
		{			
			return cb.between(root.get("comecoPlantao"), LocalDateTime.now(),data);
		};
	} 
	
	public static Specification<Policiamento> entreDatas(LocalDateTime inicioRange, LocalDateTime fimRange) {
		return (root, query, cb) ->
		{			
			return cb.between(root.get("comecoPlantao"), inicioRange, fimRange);
		};
	} 

	public static Specification<Policiamento> filtro(Policiamento policiamento, Estabelecimento estabelecimento) {
		return (root, query, cb) ->
		{
			List<Predicate> predicates = new ArrayList<>();
		
			if (policiamento.getCidade() != null) {
				predicates.add(cb.equal(root.get("cidade"), policiamento.getCidade()));
			}
			if (policiamento.getLocalidade() != null) {
				predicates.add(cb.equal(root.get("localidade"),  policiamento.getLocalidade()));
			}
			if (policiamento.getBairro() != null) {
				predicates.add(cb.equal(root.get("bairro"),  policiamento.getBairro()));
			}
			if (policiamento.getMesa().getNome() != null) {
				predicates.add(cb.like(root.get("mesa").get("nome").as(String.class), "%"+policiamento.getMesa().getNome()+"%"));
			}			
			if (policiamento.getUnidade() != null) {
				predicates.add(cb.equal(root.get("unidade"), policiamento.getUnidade()));
			}
			
			if (policiamento.getModalidade() != null) {
				predicates.add(cb.equal(root.get("modalidade"), policiamento.getModalidade()));
			}
			
			predicates.add(cb.between(root.get("comecoPlantao"), policiamento.getComecoPlantao(), policiamento.getTerminoPlantao()));
			
			predicates.add(cb.equal(root.get("mesa").get("estabelecimento").as(Estabelecimento.class),  estabelecimento));
			
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));		
		}; 
	}
	
}
