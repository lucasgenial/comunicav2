package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom16;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom16Repository;

@Service
public class Cicom16Service {

	@Autowired
	private Cicom16Repository repository;

	public Optional<Cicom16> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom16> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom16>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
