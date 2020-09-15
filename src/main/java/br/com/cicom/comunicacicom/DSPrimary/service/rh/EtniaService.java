package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Etnia;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.EtniaRepository;

@Service
public class EtniaService {

	@Autowired
	private EtniaRepository repositorio;

	public Etnia cadastrar(Etnia Etnia) {
		return repositorio.saveAndFlush(Etnia);
	}

	public Etnia alterar(Long id, Etnia Etnia) {
		Etnia EtniaBanco = repositorio.findById(id).get();

		if (EtniaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(Etnia, EtniaBanco, "id");

		return repositorio.saveAndFlush(EtniaBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Etnia> buscaPorId(Long id) {
		return (Optional<Etnia>) repositorio.findById(id);
	}

	public List<Etnia> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

}
