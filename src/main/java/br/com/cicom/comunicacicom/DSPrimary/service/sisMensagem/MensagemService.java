package br.com.cicom.comunicacicom.DSPrimary.service.sisMensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Mensagem;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisMensagem.MensagemRepository;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository repositorio;
	
	
	public Mensagem cadastrar(Mensagem mensagem) {
		return repositorio.saveAndFlush(mensagem);
	}
}
