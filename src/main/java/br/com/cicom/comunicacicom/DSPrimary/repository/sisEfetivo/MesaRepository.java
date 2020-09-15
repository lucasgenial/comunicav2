package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncaoInterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> , QueryByExampleExecutor<Mesa>, DataTablesRepository<Mesa, Long>{
	List<Mesa> findAllByAtivoAndNomeLike(Boolean ativo, String nome); 
	List<Mesa> findAllByNomeLike(String nome);
	List<Mesa> findAllByNomeLikeAndAtivoAndServidoresServidorId(String nome, Boolean ativo, Long idServidor); 
	List<Mesa> findByEstabelecimentoInAndInicioPlantaoGreaterThanEqualAndFimPlantaoLessThanEqualAndNomeLike(List<Estabelecimento> estabelecimento, LocalDateTime dataInicio, LocalDateTime dataFim, String nome);
	List<Mesa> findByEstabelecimentoInAndInicioPlantaoGreaterThanEqualAndFimPlantaoLessThanEqualAndNomeLikeAndServidoresIn(List<Estabelecimento> estabelecimento, LocalDateTime dataInicio, LocalDateTime dataFim, String nome, List<ServidorFuncaoInterno> servidores);
	
	@Query(value = "SELECT COUNT(*) FROM tbl_mesa as Me where Me.nome like :nome", nativeQuery = true)
	public Integer buscarQuantidadeDeMesasPorNomeEstabelecimento(@Param("nome") String nome);

}