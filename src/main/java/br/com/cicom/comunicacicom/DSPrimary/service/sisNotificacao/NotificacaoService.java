package br.com.cicom.comunicacicom.DSPrimary.service.sisNotificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisNotificacao.Notificacao;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisNotificacao.NotificacaoRepository;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository repositorio;
	
	
	public Notificacao cadastrar(Notificacao notificacao) {
		return repositorio.saveAndFlush(notificacao);
	}
}
