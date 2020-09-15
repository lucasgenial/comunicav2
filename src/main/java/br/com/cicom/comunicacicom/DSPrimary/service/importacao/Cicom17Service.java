package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom17;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom17Repository;

@Service
public class Cicom17Service {

	@Autowired
	private Cicom17Repository repository;

	public Optional<Cicom17> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom17> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom17>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
