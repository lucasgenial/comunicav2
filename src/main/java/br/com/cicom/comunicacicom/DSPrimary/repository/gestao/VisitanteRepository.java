package br.com.cicom.comunicacicom.DSPrimary.repository.gestao;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;

@Repository
public interface VisitanteRepository extends DataTablesRepository<Visitante, Long>, JpaRepository<Visitante, Long> {
	Visitante findByCpf(String cpf);

}