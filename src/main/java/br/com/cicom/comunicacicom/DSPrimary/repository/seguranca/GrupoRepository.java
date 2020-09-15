package br.com.cicom.comunicacicom.DSPrimary.repository.seguranca;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;

@Repository
public interface GrupoRepository extends DataTablesRepository<Grupo, Long>, JpaRepository<Grupo, Long> {
	
	Grupo findByNome(String value);

}