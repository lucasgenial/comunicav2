package br.com.cicom.comunicacicom.DSPrimary.repository.gestao;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visita;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

@Repository
public interface VisitaRepository extends DataTablesRepository<Visita, Long>, JpaRepository<Visita, Long> {
	
	List<Visita> findByEstabelecimento(Estabelecimento estabelecimento);
}