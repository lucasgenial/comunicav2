package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom13;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom13Repository;

@Service
public class Cicom13Service {

	@Autowired
	private Cicom13Repository repository;

	public Optional<Cicom13> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom13> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom13>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
