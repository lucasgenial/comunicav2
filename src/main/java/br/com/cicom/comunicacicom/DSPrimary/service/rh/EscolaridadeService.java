package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Escolaridade;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.EscolaridadeRepository;

@Service
public class EscolaridadeService  {

	@Autowired
	private EscolaridadeRepository repositorio;

	public Escolaridade cadastrar(Escolaridade Escolaridade) {
		return repositorio.saveAndFlush(Escolaridade);
	}

	public Escolaridade alterar(Long id, Escolaridade Escolaridade) {
		Escolaridade EscolaridadeBanco = repositorio.findById(id).get();

		if (EscolaridadeBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(Escolaridade, EscolaridadeBanco, "id");

		return repositorio.saveAndFlush(EscolaridadeBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Escolaridade> buscaPorId(Long id) {
		return (Optional<Escolaridade>) repositorio.findById(id);
	}

	public List<Escolaridade> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

}
