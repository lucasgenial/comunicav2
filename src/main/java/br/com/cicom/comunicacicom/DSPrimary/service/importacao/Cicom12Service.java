package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom12;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom12Repository;

@Service
public class Cicom12Service {

	@Autowired
	private Cicom12Repository repository;


	public Optional<Cicom12> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public Optional<Cicom12> buscarPorId(Long id) {
		this.repository.flush();
		return repository.findById(id);
	}

	public List<Cicom12> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom12>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}
}
