package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

@Repository
public interface PoliciamentoRepository extends JpaRepository<Policiamento, Long> , QueryByExampleExecutor<Policiamento>, DataTablesRepository<Policiamento, Long> {
	
	List<Policiamento> findAllByCidadeIn(List<Cidade> cidade);

	List<Policiamento> findAllByCidadeInAndTerminoPlantaoAfter(List<Cidade> cidade, LocalDateTime horaComecoMesa);
	
	List<Policiamento> findAllByAtivo(Boolean ativo);
	
	List<Policiamento> findAllByBaixado(Boolean ativo);
	
	
	@Query(value = "SELECT "
			+"		POL.id AS ID "
			+"FROM "
			+"		tbl_policiamento AS POL "
			+"		INNER JOIN tbl_mesa AS MESA ON MESA.id = POL.mesa "
			+"WHERE "
			+"		POL.baixado = false "
			+"AND "
			+"		MESA.estabelecimento_id = :estabelecimento "
			+"ORDER BY "
			+"		POL.fim_plantao ASC;", nativeQuery = true)
	List<BigInteger> listarEfetivoExternoAtivoPorEstabelecimento(@Param("estabelecimento")Estabelecimento estabelecimento);
	
	@Query(value = "SELECT " 
			+"	POL.cidade, " 
			+"    POL.modalidade_fk, " 
			+"    POL.id "
			+"FROM  "
			+"	tbl_policiamento AS POL " 
			+"	RIGHT JOIN ( "
			+"		SELECT *  FROM tbl_mesa AS MESA WHERE ( ativo = true AND fim_plantao >= :dataAtual) " 
			+"	) AS MESA ON MESA.id = POL.mesa "
			+"WHERE "
			+"	POL.fim_plantao >= :dataAtual " 
			+"AND "
			+"	POL.baixado = false " 
			+"AND "
			+"	MESA.estabelecimento_id = :estabelecimento " 
			+"ORDER BY "
			+"	cidade, modalidade_fk, id ASC;", nativeQuery = true)
	List<Object[]> listarUltimosPoliciamento(@Param("dataAtual") LocalDateTime dataAtual, @Param("estabelecimento") Estabelecimento estabelecimento);
	

	/*
	 * @Query(value ="SELECT " + "	modal.nome AS MODALIDADE, " +
	 * "	und.nome AS UNIDADE, " + "	cid.nome AS CIDADE, " +
	 * "	SUM(CASE WHEN (guar.policiamento_id_fk = pol.id) THEN 1 ELSE 0 END) AS totalServidores, "
	 * + "	COUNT(DISTINCT pol.id ) AS totalModalidades " +
	 * "FROM tbl_policiamento AS pol " +
	 * "	RIGHT JOIN tbl_mesa AS mesa ON mesa.id = pol.mesa " +
	 * "	RIGHT JOIN unidade AS und ON und.id = pol.unidade " +
	 * "	RIGHT JOIN cidade AS cid ON cid.id = pol.cidade " +
	 * "	RIGHT JOIN tbl_guarnicao AS guar ON guar.policiamento_id_fk = pol.id " +
	 * "	RIGHT JOIN tbl_modalidade AS modal ON modal.id = pol.modalidade_fk " +
	 * "WHERE " + "	mesa.ativo = true " + "AND " +
	 * "	mesa.estabelecimento_id = :estabelecimentos " + "GROUP BY "+
	 * "	modal.nome, und.nome, cid.nome;", nativeQuery = true) List<EfetivoPOJO>
	 * buscarPoliciamentoAtivoPorEstabelecimento(@Param("estabelecimentos")
	 * Estabelecimento estabelecimentos);
	 */

	@Query(value="SELECT " + 
			"	 TBL1.cidade AS CIDADE, " + 
			"    TBL1.id_cidade AS ID_CIDADE, " + 
			"    TBL1.modalidade AS MODALIDADE, " + 
			"    TBL1.id_modalidade AS ID_MODALIDADE, " + 
			"    TBL1.instituicao AS INSTITUICAO, " + 
			"    TBL1.id_instituicao AS ID_INSTITUICAO, " + 
			"    SUM(TBL1.qtd_servidores) AS QTD_SERVIDORES, " + 
			"    COUNT(TBL1.modalidade) AS QTD_MODALIDADE " + 
			"FROM " + 
			"	( " + 
			"		SELECT " + 
			"			cidade.nome AS CIDADE, " + 
			"           cidade.id AS ID_CIDADE, " + 
			"			tbl_modalidade.nome AS MODALIDADE, " + 
			"           tbl_modalidade.id AS ID_MODALIDADE, " + 
			"			instituicao.nome AS INSTITUICAO, " + 
			"           instituicao.id AS ID_INSTITUICAO, " + 
			"			COUNT(tbl_policiamento.id) AS QTD_SERVIDORES, " + 
			"			tbl_policiamento.id AS ID_POLICIAMENTO " + 
			"		FROM tbl_guarnicao " + 
			"			JOIN tbl_policiamento ON tbl_guarnicao.policiamento_id_fk = tbl_policiamento.id " + 
			"			JOIN cidade ON tbl_policiamento.cidade = cidade.id " + 
			"			JOIN unidade ON tbl_policiamento.unidade = unidade.id " + 
			"			JOIN instituicao ON unidade.instituicao = instituicao.id " + 
			"			JOIN tbl_modalidade ON tbl_policiamento.modalidade_fk = tbl_modalidade.id " + 
			"			JOIN tbl_servidor_funcao ON tbl_guarnicao.servidor_id_fk = tbl_servidor_funcao.id " + 
			"			JOIN tbl_servidor ON tbl_servidor_funcao.servidor = tbl_servidor.id " + 
			"		WHERE " + 
			"			tbl_policiamento.ativo = true  AND " + 
			"           tbl_policiamento.cidade IN :cidades" + 
			"		GROUP BY " + 
			"			tbl_guarnicao.policiamento_id_fk " + 
			"	) AS TBL1 " + 
			"GROUP BY " + 
			"	TBL1.cidade, " + 
			"	TBL1.modalidade, " + 
			"   TBL1.instituicao", nativeQuery = true)
	List<Object[]> listarPoliciamentoDashboard(@Param("cidades") List<Cidade> cidade);
}