package br.com.cicom.comunicacicom.DSPrimary.repository.gestao;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Servico;

@Repository
public interface ServicoRepository extends DataTablesRepository<Servico, Long>,JpaRepository <Servico, Long> {
	Servico findByNome(String nome);

}
