package br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;

@Repository
public interface TipificacaoRepository extends DataTablesRepository<Tipificacao, Long>, JpaRepository<Tipificacao, Long> {

	Tipificacao findByNome(String nome);
	
	List<Tipificacao> findAllByCategoriaIdIn(List<Long> idsDeCategoria);

	List<Tipificacao> findAllByCategoriaIn(List<Categoria> idsDeCategoria);

}