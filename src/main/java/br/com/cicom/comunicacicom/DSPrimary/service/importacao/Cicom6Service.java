package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cecoco;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom6;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom6Repository;

@Service
public class Cicom6Service extends Cecoco {

	@Autowired
	private Cicom6Repository repository;

	public Optional<Cicom6> buscaPorId(Long id) {
		this.repository.flush();
		return (Optional<Cicom6>)repository.findById(id.longValue());
	}

	public List<Cicom6> buscaPorIdeEstabelecimento(Long id, Long estabelecimento) {
		this.repository.flush();
		return (List<Cicom6>)repository.findByIdAndEstabelecimento(id.longValue(), estabelecimento.longValue());
	}

	public List<Cicom6> listarTodos() {
		return repository.findAll();
	}
}
