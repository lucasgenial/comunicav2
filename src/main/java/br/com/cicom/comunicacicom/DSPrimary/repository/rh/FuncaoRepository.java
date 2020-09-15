package br.com.cicom.comunicacicom.DSPrimary.repository.rh;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Funcao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;

@Repository
public interface FuncaoRepository extends DataTablesRepository<Funcao, Long>, JpaRepository<Funcao, Long> {
 
	List<Funcao> findByTipoFuncao(TipoFuncao TipoFuncao);
	List<Funcao> findByPrioridade(boolean prioridadeMesa);

}
