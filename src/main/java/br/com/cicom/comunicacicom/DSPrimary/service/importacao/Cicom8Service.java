package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom8;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom8Repository;

@Service
public class Cicom8Service {

	@Autowired
	private Cicom8Repository repository;

	public Optional<Cicom8> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom8> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom8>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
