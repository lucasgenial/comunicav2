package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom10;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom10Repository;

@Service
public class Cicom10Service {

	@Autowired
	private Cicom10Repository repository;

	
	public Optional<Cicom10> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public Optional<Cicom10> buscarPorId(Long id) {
		this.repository.flush();
		return repository.findById(id);
	}

	public List<Cicom10> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom10>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
