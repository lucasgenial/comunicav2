package br.com.cicom.comunicacicom.DSPrimary.repository.sisMensagem;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Notificacao;

@Repository
public interface NotificacaoRepository extends DataTablesRepository<Notificacao, Long>, JpaRepository<Notificacao, Long> {

}