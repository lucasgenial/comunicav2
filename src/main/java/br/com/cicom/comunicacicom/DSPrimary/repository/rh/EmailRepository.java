package br.com.cicom.comunicacicom.DSPrimary.repository.rh;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;

@Repository
public interface EmailRepository extends DataTablesRepository<Email, Long>, JpaRepository<Email, Long>{
	Email findByEnderecoDeEmail(String EnderecoEmail);
}
