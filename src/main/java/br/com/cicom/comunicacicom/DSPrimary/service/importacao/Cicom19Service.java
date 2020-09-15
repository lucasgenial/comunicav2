package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom19;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom19Repository;

@Service
public class Cicom19Service {

	@Autowired
	private Cicom19Repository repository;

	public Optional<Cicom19> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom19> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom19>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
