package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoServico;

@Repository
public interface TipoServicoRepository extends DataTablesRepository<TipoServico, Long>, JpaRepository<TipoServico, Long> {

}