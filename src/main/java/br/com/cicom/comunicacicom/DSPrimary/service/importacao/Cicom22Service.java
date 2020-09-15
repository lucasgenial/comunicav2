package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom22;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom22Repository;

@Service
public class Cicom22Service {

	@Autowired
	private Cicom22Repository repository;

	public Optional<Cicom22> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom22> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom22>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
