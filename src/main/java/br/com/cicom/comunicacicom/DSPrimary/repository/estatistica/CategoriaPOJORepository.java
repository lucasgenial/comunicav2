package br.com.cicom.comunicacicom.DSPrimary.repository.estatistica;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.POJO.CategoriaPOJO;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;

@Repository
public interface CategoriaPOJORepository extends JpaRepository<Categoria, Long> {
	
	@Query(value="SELECT " + 
			"	distinct CAT.nome AS categoria, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 1) THEN 1 ELSE 0 END) AS janeiro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 2) THEN 1 ELSE 0 END) AS fevereiro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 3) THEN 1 ELSE 0 END) AS marco, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 4) THEN 1 ELSE 0 END) AS abril, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 5) THEN 1 ELSE 0 END) AS maio, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 6) THEN 1 ELSE 0 END) AS junho, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 7) THEN 1 ELSE 0 END) AS julho, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 8) THEN 1 ELSE 0 END) AS agosto, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 9) THEN 1 ELSE 0 END) AS setembro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 10) THEN 1 ELSE 0 END) AS outubro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 11) THEN 1 ELSE 0 END) AS novembro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 12) THEN 1 ELSE 0 END) AS dezembro, " + 
			"	COUNT(OCO.id) AS total " + " FROM  ocorrencia AS OCO " +
			"	RIGHT JOIN cidade AS CID ON OCO.estabelecimento_id = CID.estabelecimento_id " + 
			"	RIGHT JOIN tipificacao AS TPO ON OCO.tipificacao = TPO.id " + 
			"	RIGHT JOIN categoria AS CAT ON CAT.id = TPO.categoria_id " + " WHERE " + "	OCO.ativo = true " + 
			" AND " + 
			"	OCO.data_ocorrencia BETWEEN :dataInicial AND :dataFinal " + 
			" AND " + 
			"	CID.id IN :listaCidades " + 
			" AND " + 
			"	CAT.id IN :listaCategorias " +
			" GROUP BY CAT.nome;", nativeQuery = true)
	List<CategoriaPOJO> buscaDadosEstatistica(@Param("listaCidades") List<Long> listaCidades,
			@Param("listaCategorias") List<Long> listaCategorias, @Param("dataInicial") LocalDate dataInicial, 
			 @Param("dataFinal") LocalDate dataFinal);

	@Query(value="SELECT " + 
			"	TPO.nome AS categoria, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 1) THEN 1 ELSE 0 END) AS janeiro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 2) THEN 1 ELSE 0 END) AS fevereiro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 3) THEN 1 ELSE 0 END) AS marco, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 4) THEN 1 ELSE 0 END) AS abril, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 5) THEN 1 ELSE 0 END) AS maio, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 6) THEN 1 ELSE 0 END) AS junho, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 7) THEN 1 ELSE 0 END) AS julho, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 8) THEN 1 ELSE 0 END) AS agosto, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 9) THEN 1 ELSE 0 END) AS setembro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 10) THEN 1 ELSE 0 END) AS outubro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 11) THEN 1 ELSE 0 END) AS novembro, " + 
			"	SUM(CASE WHEN (MONTH(OCO.data_ocorrencia) = 12) THEN 1 ELSE 0 END) AS dezembro, " + 
			"	COUNT(OCO.id) AS total " + 
			" 	FROM  ocorrencia AS OCO " +
			"	INNER JOIN tipificacao AS TPO ON TPO.id = OCO.tipificacao " + 
			"   INNER JOIN endereco AS END ON END.id = OCO.endereco " +
			"	INNER JOIN categoria AS CAT ON CAT.id = TPO.categoria_id " + 
			" WHERE OCO.ativo = true " + 
			" AND OCO.data_ocorrencia >= :dataInicial " +
			" AND OCO.data_ocorrencia <= :dataFinal " + 
			" AND END.cidade IN :listaCidades " + 
			" AND CAT.id IN :listaCategorias " +
			" GROUP BY TPO.nome;", nativeQuery = true)
	List<CategoriaPOJO> buscaDadosEstatisticaTipo(@Param("listaCidades") List<Long> listaCidades,
			@Param("listaCategorias") List<Long> listaCategorias, @Param("dataInicial") LocalDate dataInicial, 
			 @Param("dataFinal") LocalDate dataFinal);

}