package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom18;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom18Repository;

@Service
public class Cicom18Service {

	@Autowired
	private Cicom18Repository repository;

	public Optional<Cicom18> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom18> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom18>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
