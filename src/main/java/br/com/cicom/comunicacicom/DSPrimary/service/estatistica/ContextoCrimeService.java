package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.ContextoCrime;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.ContextoCrimeRepository;

@Service
public class ContextoCrimeService {
	@Autowired
	private ContextoCrimeRepository repositorio;

	public ContextoCrime cadastrar(ContextoCrime contexto) {
		return repositorio.saveAndFlush(contexto);
	}

	public ContextoCrime alterar(Long id, ContextoCrime contexto) {
		ContextoCrime contextoBanco = repositorio.getOne(id);

		if (contextoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(contexto, contextoBanco, "id");

		return repositorio.saveAndFlush(contextoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<ContextoCrime> buscaPorId(Long id) {
		return (Optional<ContextoCrime>) repositorio.findById(id);
	}

	public List<ContextoCrime> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public ContextoCrime desativar(Long id, boolean situacao) {
		ContextoCrime contexto = repositorio.findById(id).get();

		if (contexto != null) {
			contexto.setAtivo(situacao);
		}

		return repositorio.save(contexto);
	}

}
