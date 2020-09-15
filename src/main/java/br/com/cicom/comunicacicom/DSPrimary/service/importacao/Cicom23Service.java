package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom23;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom23Repository;

@Service
public class Cicom23Service {

	@Autowired
	private Cicom23Repository repository;

	public Optional<Cicom23> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom23> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom23>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}