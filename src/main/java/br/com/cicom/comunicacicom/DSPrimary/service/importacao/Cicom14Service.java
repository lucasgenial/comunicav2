package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom14;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom14Repository;

@Service
public class Cicom14Service {
	
	@Autowired
	private Cicom14Repository repository;
	
	public Optional<Cicom14> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom14> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom14>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
