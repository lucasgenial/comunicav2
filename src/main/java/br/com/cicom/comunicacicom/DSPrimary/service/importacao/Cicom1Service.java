package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom1;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom1Repository;

@Service
public class Cicom1Service {

	@Autowired
	private Cicom1Repository repository;

	public Optional<Cicom1> buscaPorId(Long id) {
		repository.flush();
		return repository.findById(id);
	}

	public List<Cicom1> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom1>) repository.findByIdAndEstabelecimento(id.longValue(), estabelecimento.longValue());
	}

}
