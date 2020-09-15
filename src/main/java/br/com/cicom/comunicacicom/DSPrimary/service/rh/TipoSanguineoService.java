package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.TipoSanguineo;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.TipoSanguineoRepository;

@Service
public class TipoSanguineoService {

	@Autowired
	private TipoSanguineoRepository repositorio;

	public TipoSanguineo cadastrar(TipoSanguineo TipoSanguineo) {
		return repositorio.saveAndFlush(TipoSanguineo);
	}

	public TipoSanguineo alterar(Long id, TipoSanguineo TipoSanguineo) {
		TipoSanguineo TipoSanguineoBanco = repositorio.findById(id).get();

		if (TipoSanguineoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(TipoSanguineo, TipoSanguineoBanco, "id");

		return repositorio.saveAndFlush(TipoSanguineoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<TipoSanguineo> buscaPorId(Long id) {
		return (Optional<TipoSanguineo>) repositorio.findById(id);
	}

	public List<TipoSanguineo> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

}
