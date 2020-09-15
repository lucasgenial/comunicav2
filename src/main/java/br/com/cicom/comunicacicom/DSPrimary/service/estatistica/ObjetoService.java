package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Objeto;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.ObjetoRepository;

@Service
public class ObjetoService {

	@Autowired
	private ObjetoRepository repositorio;

	public Objeto cadastrar(Objeto objeto) {
		return repositorio.saveAndFlush(objeto);
	}

	public Objeto alterar(Long id, Objeto objeto) {
		Objeto objetoBanco = repositorio.getOne(id);

		if (objetoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(objeto, objetoBanco, "id");

		return repositorio.saveAndFlush(objetoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Objeto> buscaPorId(Long id) {
		return (Optional<Objeto>) repositorio.findById(id);
	}

	public List<Objeto> listarTodos() {
		return repositorio.findAll();
	}
}
