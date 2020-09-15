package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom15;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom15Repository;

@Service
public class Cicom15Service {

	@Autowired
	private Cicom15Repository repository;
	
	public Optional<Cicom15> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom15> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom15>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
