package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom11;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom11Repository;

@Service
public class Cicom11Service {

	@Autowired
	private Cicom11Repository repository;
	
	public Optional<Cicom11> buscaPorId(Long id) {
		return repository.findById(id);
	}

	public List<Cicom11> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom11>) repository.findByIdAndEstabelecimento(id, estabelecimento);
	}

}
