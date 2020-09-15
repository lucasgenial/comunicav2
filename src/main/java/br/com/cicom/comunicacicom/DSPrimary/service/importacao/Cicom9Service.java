package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom9;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom9Repository;

@Service
public class Cicom9Service {

	@Autowired
	private Cicom9Repository repository;

	public Optional<Cicom9> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom9> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom9>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
