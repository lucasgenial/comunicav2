package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom3;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom3Repository;

@Service
public class Cicom3Service {

	@Autowired
	private Cicom3Repository repository;

	public Optional<Cicom3> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom3> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom3>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
