package br.com.cicom.comunicacicom.DSPrimary.service.sisNotificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisNotificacao.Mensagem;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisNotificacao.MensagemRepository;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository repositorio;
	
	
	public Mensagem cadastrar(Mensagem mensagem) {
		return repositorio.saveAndFlush(mensagem);
	}
}
