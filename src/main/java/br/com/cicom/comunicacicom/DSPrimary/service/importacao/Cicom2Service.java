package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom2;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom2Repository;

@Service
public class Cicom2Service {

	@Autowired
	private Cicom2Repository repository;

	public Optional<Cicom2> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}
	public Optional<Cicom2> buscarPorId(Long id) {
		this.repository.flush();
		return repository.findById(id);
	}

	public List<Cicom2> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom2>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
