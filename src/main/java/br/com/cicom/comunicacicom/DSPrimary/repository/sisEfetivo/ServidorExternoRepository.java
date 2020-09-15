package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorExterno;

@Repository
public interface ServidorExternoRepository extends DataTablesRepository<ServidorExterno, Long>,JpaRepository<ServidorExterno, Long> {
	ServidorExterno findByMatriculaAndAtivo(String matricula, boolean ativo);
}