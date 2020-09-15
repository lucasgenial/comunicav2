package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Caracteristica;

@Repository//public interface CaracteristicaRepository 

public interface CaracteristicaRepository extends DataTablesRepository<Caracteristica, Long>, JpaRepository<Caracteristica, Long>, JpaSpecificationExecutor<Caracteristica> {

}