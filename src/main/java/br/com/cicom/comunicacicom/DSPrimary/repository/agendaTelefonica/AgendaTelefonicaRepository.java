package br.com.cicom.comunicacicom.DSPrimary.repository.agendaTelefonica;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.agendaTelefonica.AgendaTelefonica;

@Repository
public interface AgendaTelefonicaRepository extends DataTablesRepository<AgendaTelefonica, Long>, JpaRepository<AgendaTelefonica, Long>{

}
