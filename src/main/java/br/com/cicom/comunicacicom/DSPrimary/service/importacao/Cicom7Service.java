package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom7;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom7Repository;

@Service
public class Cicom7Service {

	@Autowired
	private Cicom7Repository repository;

	public Optional<Cicom7> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public Optional<Cicom7> buscarPorId(Long id) {
		this.repository.flush();
		return repository.findById(id);
	}

	public List<Cicom7> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom7>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
