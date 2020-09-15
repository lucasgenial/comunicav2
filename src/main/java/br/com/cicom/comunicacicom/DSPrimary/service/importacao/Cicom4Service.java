package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom4;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom4Repository;

@Service
public class Cicom4Service {

	@Autowired
	private Cicom4Repository repository;

	public Optional<Cicom4> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public Optional<Cicom4> buscarPorId(Long id) {
		this.repository.flush();
		return repository.findById(id);
	}

	public List<Cicom4> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom4>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
