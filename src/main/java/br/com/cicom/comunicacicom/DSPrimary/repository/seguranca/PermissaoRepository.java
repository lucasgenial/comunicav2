package br.com.cicom.comunicacicom.DSPrimary.repository.seguranca;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;

@Repository
public interface PermissaoRepository extends DataTablesRepository<Permissao, Long>, JpaRepository<Permissao, Long> {
	
	@Query(value = "select gp.id_permissoes from grupos_permissoes AS gp where gp.id_grupo= :idGrupo", nativeQuery = true)
	Long[] listaDeIdsDePermissoesDeUmGrupo(@Param("idGrupo") Long idGrupo);
	
	
}