package br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

@Repository
public interface OcorrenciaRepository extends DataTablesRepository<Ocorrencia, Long>, JpaRepository<Ocorrencia, Long>, JpaSpecificationExecutor<Ocorrencia> {

	List<Ocorrencia> findBySic(String value);
	
	List<Ocorrencia> findByEstabelecimento(Estabelecimento estabelecimento);
	
	List<Ocorrencia> findByDataOcorrenciaGreaterThanEqual(LocalDate dataOcorrencia);

	List<Ocorrencia> findByEstabelecimentoAndDataOcorrenciaGreaterThanEqual(Estabelecimento estabelecimento, LocalDateTime dataOcorrencia);

	@Query(value ="SELECT Oc.sic AS sic, "
				+ " DATE_FORMAT(Oc.data_ocorrencia,'%d/%m/%Y %H:%i')  AS dataOcorrencia, "
				+ " Ti.nome AS tipificacao, "
				+ " Oc.descricao AS descricao, "
				+ " Oc.enviada AS enviada, "
				+ " Loc.nome As localidade, "
				+ " Ba.nome As bairro, "
				+ " Ci.nome As cidade, "
				+ " Oc.id As id, "
				+ " Es.nome As estabelecimento  FROM "
				+ " ocorrencia AS Oc "
				+ "	INNER JOIN tipificacao AS Ti ON Oc.tipificacao = Ti.id "
				+ "	INNER JOIN endereco AS En ON Oc.endereco = En.id " 
				+ "	INNER JOIN cidade AS Ci ON En.cidade = Ci.id "
				+ "	INNER JOIN bairro AS Ba ON En.bairro = Ba.id " 
				+ "	INNER JOIN estabelecimento AS Es ON Oc.estabelecimento_id = Es.id " 
				+ "	INNER JOIN localidade AS Loc ON Ba.localidade_id = Loc.id " 
				+ " WHERE ( OC.data_ocorrencia BETWEEN :dataAnterior AND :dataAtual ) "
				+ "	AND OC.estabelecimento_id = :estabelecimento " , nativeQuery = true)
	List<Map<String,Object>> ListOcorrenciasUntimos3Dias(@Param("dataAnterior") LocalDateTime dataAnterior, @Param("dataAtual") LocalDateTime dataAtual, @Param("estabelecimento") Estabelecimento estabelecimento);
	
	
	
	Page<Ocorrencia> findBySicLikeOrTipificacaoNomeLikeOrDescricaoLikeOrEnderecoCidadeNomeLikeOrEnderecoBairroNomeLikeOrEnderecoBairroLocalidadeNomeLikeOrderByIdDesc(
			String sic, String nomeTipificacao, String nomeDescricao, String nomeCidade, String nomeBairro,
			String nomeLocalidade, Pageable pageable);

	Page<Ocorrencia> findByEstabelecimentoAndTipificacaoNomeLikeOrDescricaoLikeOrEnderecoCidadeNomeLikeOrEnderecoBairroNomeLikeOrEnderecoBairroLocalidadeNomeLikeOrSicLikeOrderByIdDesc(
			Estabelecimento estabelecimento, String sic, String nomeTipificacao, String nomeDescricao,
			String nomeCidade, String nomeBairro, String nomeLocalidade, Pageable pageable);

	@Query(value = "SELECT * FROM ocorrencia ocorrencia0_ "
			+ " LEFT OUTER JOIN tipificacao tipificaca1_ ON ocorrencia0_.tipificacao=tipificaca1_.id "
			+ " LEFT OUTER JOIN endereco endereco2_ ON ocorrencia0_.endereco=endereco2_.id "
			+ " LEFT OUTER JOIN cidade cidade3_ ON endereco2_.cidade=cidade3_.id "
			+ " LEFT OUTER JOIN bairro bairro4_ ON endereco2_.bairro=bairro4_.id"
			+ " LEFT OUTER JOIN localidade localidade5_ ON bairro4_.localidade_id=localidade5_.id "
			+ " WHERE ocorrencia0_.estabelecimento_id= :estabelecimento"
			+ " AND ( tipificaca1_.nome LIKE :pesquisa OR ocorrencia0_.descricao LIKE :pesquisa OR ocorrencia0_.historico LIKE :pesquisa OR cidade3_.nome LIKE :pesquisa OR bairro4_.nome LIKE :pesquisa OR localidade5_.nome LIKE :pesquisa  OR ocorrencia0_.sic LIKE :pesquisa) ORDER BY ocorrencia0_.id DESC", nativeQuery = true)
	Page<Ocorrencia> buscaPaginadaComQuery(@Param("pesquisa") String pesquisa,
			@Param("estabelecimento") Long estabelecimento, Pageable pageable);

	@Query(value = "SELECT * FROM ocorrencia AS OC "
			+ "WHERE OC.sic = :sic AND OC.estabelecimento_id IN :estabelecimentos", nativeQuery = true)
	List<Ocorrencia> findBySicAndEstabelecimento(@Param("sic") String sic,
			@Param("estabelecimentos") List<Estabelecimento> estabelecimentos);

	@Query(value = "SELECT TI.nome AS nome, " + " count(*) " + "FROM " + "	ocorrencia AS OC "
			+ "	INNER JOIN tipificacao AS TI ON OC.tipificacao = TI.id "
			+ "	INNER JOIN endereco AS EN ON OC.endereco = EN.id " + "WHERE "
			+ "	( OC.data_ocorrencia BETWEEN :dataAnterior AND :dataAtual ) "
			+ "	AND OC.estabelecimento_id = :estabelecimento " + "GROUP BY " + "	nome", nativeQuery = true)
	List<Object[]> listaUltimasOcorrencias(@Param("dataAnterior") LocalDateTime dataAnterior,
			@Param("dataAtual") LocalDateTime dataAtual, @Param("estabelecimento") Estabelecimento estabelecimento);

	@Query(value = "SELECT " + " TIME_FORMAT(OC.data_ocorrencia, '%H:00') AS hr, " + "    COUNT(OC.id) " + "FROM "
			+ "		ocorrencia AS OC " + "	INNER JOIN endereco AS EN ON OC.endereco = EN.id "
			+ "	INNER JOIN cidade AS CI ON EN.cidade = CI.id " + "WHERE " + "    DATE(OC.data_ocorrencia) = :dia AND "
			+ "    OC.estabelecimento_id = :estabelecimento " + "GROUP BY hr", nativeQuery = true)
	List<Object[]> buscaOcorrenciasPorHora(@Param("dia") LocalDate dia,
			@Param("estabelecimento") Estabelecimento estabelecimento);

	@Query(value = "SELECT " + " 	CI.nome AS nome, " + "		count(*) AS quantidade, "
			+ "     CI.cor_grafico AS cor " + "FROM ocorrencia AS OC "
			+ "		INNER JOIN endereco AS EN ON OC.endereco = EN.id "
			+ "		INNER JOIN cidade AS CI ON EN.cidade = CI.id " + "WHERE "
			+ "    (OC.data_ocorrencia BETWEEN :dataAnterior AND :dataAtual)"
			+ "	   AND OC.estabelecimento_id = :estabelecimento "
			+ "GROUP BY CI.nome ORDER BY quantidade DESC", nativeQuery = true)
	List<Object[]> buscaRegistroPorAbrangencia(@Param("dataAnterior") LocalDateTime dataAnterior,
			@Param("dataAtual") LocalDateTime dataAtual, @Param("estabelecimento") Estabelecimento estabelecimento);

	@Query(value = "SELECT TI.nome AS tp, " + " COUNT(*) AS QTD " + "FROM " + "  Ocorrencia AS OC "
			+ "  INNER JOIN Endereco AS EN ON EN.id = OC.endereco " + "  INNER JOIN Cidade AS CI ON EN.cidade= CI.id "
			+ "  INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id "
			+ "WHERE (OC.dataOcorrencia BETWEEN :dataAnterior AND :dataAtual) "
			+ "  AND OC.tipificacao IN :tipificacao " + "  AND CI IN :cidade " + "GROUP BY " + "  OC.tipificacao "
			+ "ORDER BY " + "  tp")
	List<Object[]> buscaPorTipificaoCidades(@Param("dataAnterior") LocalDateTime data1,
			@Param("dataAtual") LocalDateTime data2, @Param("tipificacao") List<Tipificacao> listaTipificacao,
			@Param("cidade") List<Cidade> listaCidades);

	@Query(value = "SELECT TI.nome AS tp, " + " COUNT(*) AS QTD " + "FROM " + "  Ocorrencia AS OC "
			+ "  INNER JOIN Endereco AS EN ON EN.id = OC.endereco " + "  INNER JOIN Cidade AS CI ON EN.cidade= CI.id "
			+ "  INNER JOIN Localidade AS LO ON CI.id = LO.cidade "
			+ "  INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id "
			+ "WHERE (OC.dataOcorrencia BETWEEN :data1 AND :data2) "
			+ "  AND OC.tipificacao IN :tipificacao " + "  AND LO IN :localidade " + "  AND CI IN :cidade "
			+ "GROUP BY " + "  OC.tipificacao " + "ORDER BY " + "  tp")
	List<Object[]> buscaPorTipificacaoLocalidades(@Param("data1") LocalDateTime data1,
			@Param("data2") LocalDateTime data2, @Param("tipificacao") List<Tipificacao> listaTipificacao,
			@Param("cidade") List<Cidade> listaCidades, @Param("localidade") List<Localidade> listaLocalidades);

	@Query(value = "SELECT TI.nome AS tp, " + " COUNT(*) AS QTD " + "FROM " + "  Ocorrencia AS OC "
			+ "  INNER JOIN Endereco AS EN ON EN.id = OC.endereco " + "  INNER JOIN Cidade AS CI ON EN.cidade= CI.id "
			+ "  INNER JOIN Localidade AS LO ON CI.id = LO.cidade "
			+ "  INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id "
			+ "  INNER JOIN Bairro AS BA ON EN.bairro = BA.id "
			+ "WHERE (OC.dataOcorrencia BETWEEN :data1 AND :data2) "
			+ "  AND OC.tipificacao IN :tipificacao " + "  AND CI IN :cidade " + "  AND LO IN :localidade "
			+ "  AND BA IN :bairro " + "GROUP BY " + "  OC.tipificacao " + "ORDER BY " + "  tp")
	List<Object[]> buscaPorTipificacaoBairros(@Param("data1") LocalDateTime data1, @Param("data2") LocalDateTime data2,
			@Param("tipificacao") List<Tipificacao> listaTipificacao, @Param("cidade") List<Cidade> listaCidades,
			@Param("localidade") List<Localidade> listaLocalidades, @Param("bairro") List<Bairro> listaBairros);

	@Query(value = "SELECT TI.nome AS tp, " + " COUNT(*) AS QTD " + "FROM " + "  Ocorrencia AS OC "
			+ "  INNER JOIN Estabelecimento AS ES ON OC.estabelecimento= ES.id "
			+ "  INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id "
			+ "WHERE (OC.dataOcorrencia BETWEEN :data1 AND :data2) "
			+ "  AND OC.tipificacao IN :tipificacao " + "  AND ES IN :estabelecimento " + "GROUP BY "
			+ "  OC.tipificacao " + "ORDER BY " + "  tp")
	List<Object[]> buscaPorEstabelecimento(@Param("data1") LocalDateTime data1, @Param("data2") LocalDateTime data2,
			@Param("estabelecimento") List<Estabelecimento> estabelecimentos);
	@Query(value = "SELECT " + "TI.nome AS TIPIFICACAO, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 1) THEN 1 ELSE 0 END) AS JAN, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 2) THEN 1 ELSE 0 END) AS FEV, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 3) THEN 1 ELSE 0 END) AS MAR, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 4) THEN 1 ELSE 0 END) AS ABR, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 5) THEN 1 ELSE 0 END) AS MAI, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 6) THEN 1 ELSE 0 END) AS JUN, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 7) THEN 1 ELSE 0 END) AS JUL, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 8) THEN 1 ELSE 0 END) AS AGO, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 9) THEN 1 ELSE 0 END) AS STE, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 10) THEN 1 ELSE 0 END) AS OTU, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 11) THEN 1 ELSE 0 END) AS NOV, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 12) THEN 1 ELSE 0 END) AS DEZ, "
			+ "    SUM(CASE WHEN (EN.bairro.localidade.nome != 'SEDE' AND EN.bairro.localidade.nome != 'ZONA RURAL') THEN 1 ELSE 0 END) AS DEMAIS_LOCALIDADES, "
			+ "    SUM(CASE WHEN (EN.bairro.localidade.nome = 'SEDE') THEN 1 ELSE 0 END) AS SEDE, "
			+ "    SUM(CASE WHEN (EN.bairro.localidade.nome = 'ZONA RURAL') THEN 1 ELSE 0 END) AS ZONA_RURAL " + "FROM "
			+ "	Ocorrencia AS OC " + "	INNER JOIN Endereco AS EN ON EN.id = OC.endereco "
			+ "	INNER JOIN Cidade AS CI ON EN.cidade= CI.id "
			+ "	INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id " + "WHERE "
			+ "	YEAR(OC.dataOcorrencia) BETWEEN :anoInicial AND :anoFinal "
			+ "	AND MONTH(OC.dataOcorrencia) BETWEEN :mesInicial AND :mesFinal "
			+ "	AND OC.tipificacao IN :listaTipificacao " + " AND EN.cidade IN :listaCidades " + "GROUP BY "
			+ "	OC.tipificacao " + "ORDER BY " + "	TIPIFICACAO " + " ")
	
	
	List<Object[]> buscaDadosEstatistica(@Param("listaCidades") List<Cidade> listaCidades,
			@Param("listaTipificacao") List<Tipificacao> listaTipificacao, @Param("anoInicial") Integer anoInicial,
			@Param("anoFinal") Integer anoFinal, @Param("mesInicial") Integer mesInicial,
			@Param("mesFinal") Integer mesFinal);

	@Query(value = "SELECT " + "TI.nome AS TIPIFICACAO, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 1) THEN 1 ELSE 0 END) AS JAN, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 2) THEN 1 ELSE 0 END) AS FEV, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 3) THEN 1 ELSE 0 END) AS MAR, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 4) THEN 1 ELSE 0 END) AS ABR, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 5) THEN 1 ELSE 0 END) AS MAI, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 6) THEN 1 ELSE 0 END) AS JUN, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 7) THEN 1 ELSE 0 END) AS JUL, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 8) THEN 1 ELSE 0 END) AS AGO, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 9) THEN 1 ELSE 0 END) AS STE, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 10) THEN 1 ELSE 0 END) AS OTU, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 11) THEN 1 ELSE 0 END) AS NOV, "
			+ "    SUM(CASE WHEN (MONTH(OC.dataOcorrencia) = 12) THEN 1 ELSE 0 END) AS DEZ, "
			+ "    SUM(CASE WHEN (EN.bairro.localidade.nome != 'SEDE' AND EN.bairro.localidade.nome != 'ZONA RURAL') THEN 1 ELSE 0 END) AS DEMAIS_LOCALIDADES, "
			+ "    SUM(CASE WHEN (EN.bairro.localidade.nome = 'SEDE') THEN 1 ELSE 0 END) AS SEDE, "
			+ "    SUM(CASE WHEN (EN.bairro.localidade.nome = 'ZONA RURAL') THEN 1 ELSE 0 END) AS ZONA_RURAL " + "FROM "
			+ "	Ocorrencia AS OC " + "	INNER JOIN Endereco AS EN ON EN.id = OC.endereco "
			+ "	INNER JOIN Cidade AS CI ON EN.cidade= CI.id "
			+ "	INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id " + "WHERE "
			+ "	YEAR(OC.dataOcorrencia) BETWEEN :anoInicial AND :anoFinal "
			+ "	AND MONTH(OC.dataOcorrencia) BETWEEN :mesInicial AND :mesFinal "
			+ "	AND OC.tipificacao IN :listaTipificacao " + " AND EN.cidade IN :listaCidades " + "GROUP BY "
			+ "	OC.tipificacao " + "ORDER BY " + "	TIPIFICACAO " + " ")
	
	
	
	List<Object[]> buscaDadosEstatisticaAgrupadoPorMes(@Param("listaCidades") List<Cidade> listaCidades,
			@Param("listaTipificacao") List<Tipificacao> listaTipificacao, @Param("anoInicial") Integer anoInicial,
			@Param("anoFinal") Integer anoFinal, @Param("mesInicial") Integer mesInicial,
			@Param("mesFinal") Integer mesFinal);

	@Query(value = "SELECT " + "    EST.nome AS NOME, " + "	COALESCE(COUNT(OC.estado_ocorrencia), 0) AS TOTAL "
			+ "FROM " + "	(SELECT  * FROM ocorrencia AS OC "
			+ "WHERE ( OC.data_ocorrencia BETWEEN :dataAnterior AND :dataAtual) "
			+ "		AND OC.estabelecimento_id = :estabelecimento " + "    ) AS OC "
			+ "	RIGHT JOIN estado_ocorrencia AS EST ON OC.estado_ocorrencia = EST.id " + "GROUP BY "
			+ "	NOME;", nativeQuery = true)
	List<Object[]> listarUltimasOcorrenciasPorStatus(@Param("dataAnterior") LocalDateTime dataAnterior,
			@Param("dataAtual") LocalDateTime dataAtual, @Param("estabelecimento") Estabelecimento estabelecimento);

	@Query(value = "FROM Ocorrencia o WHERE tipificacao = :tipificacao AND estabelecimento = :estabelecimento AND dataOcorrencia BETWEEN :dataInicio AND :dataFim")
	public List<Ocorrencia> buscaOcorrenciaTipificacaoPorDatas(
			@Param("estabelecimento") Estabelecimento estabelecimento, @Param("tipificacao") Tipificacao tipificacao,
			@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
	
	
	
	
	@Query(value = " SELECT COUNT(*) AS QUANTIDADE " + 
			" FROM Ocorrencia AS OC "+ 
			" INNER JOIN Tipificacao AS TI ON TI.id = OC.tipificacao "	+
			" INNER JOIN Estabelecimento AS ES ON ES.id = OC.estabelecimento " + 
			" WHERE TI IN :tipificacao "+
			" AND  ES = :estabelecimento " +
			" AND YEAR(OC.dataOcorrencia) = :ano "+
			" AND  OC.id NOT IN (SELECT REG.ocorrencia FROM RegistroOcorrencia as REG)")
	public Long quantidadeDeOcorrenciasNaoRegistradas(@Param("estabelecimento") Estabelecimento estabelecimento, @Param("tipificacao") List<Tipificacao> tipificacao, @Param("ano") int ano);

	
	@Query(value = "SELECT TI.nome AS TIPIFICACAO, "
			+ "TI.id AS ID_TIPIFICACAO, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 1) THEN 1 ELSE 0 END) AS SEDEJAM, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 2) THEN 1 ELSE 0 END) AS SEDEFEV, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 3) THEN 1 ELSE 0 END) AS SEDEMAR, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 4) THEN 1 ELSE 0 END) AS SEDEABR, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 5) THEN 1 ELSE 0 END) AS SEDEMAI, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 6) THEN 1 ELSE 0 END) AS SEDEJUN, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 7) THEN 1 ELSE 0 END) AS SEDEJUL, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 8) THEN 1 ELSE 0 END) AS SEDEAGO, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 9) THEN 1 ELSE 0 END) AS SEDESET, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 10) THEN 1 ELSE 0 END) AS SEDEOUT, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 11) THEN 1 ELSE 0 END) AS SEDENOV, "
			+ "SUM(CASE WHEN (LO.nome = 'SEDE' AND MONTH(OC.dataOcorrencia) = 12) THEN 1 ELSE 0 END) AS SEDEDEZ, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 1) THEN 1 ELSE 0 END) AS ZONARURALJAM, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 2) THEN 1 ELSE 0 END) AS ZONARURALFEV, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 3) THEN 1 ELSE 0 END) AS ZONARURALMAR, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 4) THEN 1 ELSE 0 END) AS ZONARURALABR, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 5) THEN 1 ELSE 0 END) AS ZONARURALMAI, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 6) THEN 1 ELSE 0 END) AS ZONARURALJUN, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 7) THEN 1 ELSE 0 END) AS ZONARURALJUL, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 8) THEN 1 ELSE 0 END) AS ZONARURALAGO, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 9) THEN 1 ELSE 0 END) AS ZONARURALSET, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 10) THEN 1 ELSE 0 END) AS ZONARURALOUT, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 11) THEN 1 ELSE 0 END) AS ZONARURALNOV, "
			+ "SUM(CASE WHEN (LO.nome = 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 12) THEN 1 ELSE 0 END) AS ZONARURALDEZ, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 1) THEN 1 ELSE 0 END) AS OUTRASJAM, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 2) THEN 1 ELSE 0 END) AS OUTRASFEV, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 3) THEN 1 ELSE 0 END) AS OUTRASMAR, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 4) THEN 1 ELSE 0 END) AS OUTRASABR, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 5) THEN 1 ELSE 0 END) AS OUTRASMAI, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 6) THEN 1 ELSE 0 END) AS OUTRASJUN, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 7) THEN 1 ELSE 0 END) AS OUTRASJUL, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 8) THEN 1 ELSE 0 END) AS OUTRASAGO, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 9) THEN 1 ELSE 0 END) AS OUTRASSET, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 10) THEN 1 ELSE 0 END) AS OUTRASOUT, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 11) THEN 1 ELSE 0 END) AS OUTRASNOV, "
			+ "SUM(CASE WHEN (LO.nome != 'SEDE' AND LO.nome != 'ZONA RURAL' AND MONTH(OC.dataOcorrencia) = 12) THEN 1 ELSE 0 END) AS OUTRASDEZ "
			+ "FROM Ocorrencia AS OC " + "INNER JOIN Endereco AS EN ON EN.id = OC.endereco "
			+ "INNER JOIN Bairro AS BA ON BA.id = EN.bairro " + "INNER JOIN Localidade AS LO ON LO.id = BA.localidade "
			+ "INNER JOIN Cidade AS CI ON EN.cidade= CI.id " + "INNER JOIN Tipificacao AS TI ON OC.tipificacao = TI.id "
			+ "WHERE YEAR(OC.dataOcorrencia) = :ano " + "AND CI IN :listaCidades " + "AND TI IN :listaTipificacao "
			+ "GROUP BY OC.tipificacao " + "ORDER BY	TIPIFICACAO")
	List<Object[]> buscaDadosEstatisticaPorMesECategoria(@Param("listaCidades") List<Cidade> listaCidades,
			@Param("listaTipificacao") List<Tipificacao> listaTipificacao, @Param("ano") Integer ano);

}