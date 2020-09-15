package br.com.cicom.comunicacicom.DSPrimary.repository.localizacao;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;

@Repository
public interface LocalidadeRepository extends DataTablesRepository<Localidade, Long>, JpaRepository<Localidade	, Long> {

	List<Localidade> findByCidade(Cidade cidade);

	Localidade findByCidadeAndNome(Cidade cidade, String nome);
	
}