package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Modalidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

@Repository
public interface ModalidadeRepository extends DataTablesRepository<Modalidade, Long>, JpaRepository<Modalidade, Long> {
	List<Modalidade> findAllByInstituicoes(Instituicao instituicao);

	List<Modalidade> findAllByAtivo(Boolean status);
	
	
	
	@Query(value = "SELECT tbl.contagem_modalidades,"
				+ " tbl.contagem_servidor_modalidade , "
				+ " ci.nome AS nomeCidade, "
				+ " un.nome AS nomeUnidade, "
				+ " mo.nome as nomeModalidade "
				+ " FROM tbl_policiamento_tabela AS tbl "
				+ " INNER JOIN cidade as ci on ci.id = tbl.cidade_id "
				+ " INNER JOIN instituicao as un on un.id = instituicao_id"
				+ " INNER JOIN tbl_modalidade as mo on mo.id = tbl.modalidade_id "
				+ " where tbl.estabelecimento_id = :estabelecimento", nativeQuery = true)
	List<Object> buscarDadosDaTabelaGraficoEfetivo(@Param("estabelecimento") Long estabelecimento);
}