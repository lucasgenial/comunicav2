package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Recurso;

@Repository
public interface RecursoRepository extends DataTablesRepository<Recurso, Long>, JpaRepository<Recurso, Long> {
	List<Recurso> findAllByIdIn(List<Long> ids);
}