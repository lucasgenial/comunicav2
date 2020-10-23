package br.com.cicom.comunicacicom.DSPrimary.service.sisMensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Notificacao;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisMensagem.NotificacaoRepository;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository repositorio;
	
	
	public Notificacao cadastrar(Notificacao notificacao) {
		return repositorio.saveAndFlush(notificacao);
	}
}
