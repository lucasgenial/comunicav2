package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom20;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom20Repository;

@Service
public class Cicom20Service {

	@Autowired
	private Cicom20Repository repository;
	
	public Optional<Cicom20> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom20> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom20>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
