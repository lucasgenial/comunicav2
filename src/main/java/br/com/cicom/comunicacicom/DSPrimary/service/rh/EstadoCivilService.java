package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.EstadoCivil;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.EstadoCivilRepository;

@Service
public class EstadoCivilService {

	@Autowired
	private EstadoCivilRepository repositorio;

	public EstadoCivil cadastrar(EstadoCivil EstadoCivil) {
		return repositorio.saveAndFlush(EstadoCivil);
	}

	public EstadoCivil alterar(Long id, EstadoCivil EstadoCivil) {
		EstadoCivil EstadoCivilBanco = repositorio.findById(id).get();

		if (EstadoCivilBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(EstadoCivil, EstadoCivilBanco, "id");

		return repositorio.saveAndFlush(EstadoCivilBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<EstadoCivil> buscaPorId(Long id) {
		return (Optional<EstadoCivil>) repositorio.findById(id);
	}

	public List<EstadoCivil> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

}
