package br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.EstadoOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia.EstadoOcorrenciaRepository;

@Service
public class EstadoOcorrenciaService {

	@Autowired
	private EstadoOcorrenciaRepository repositorio;

	public EstadoOcorrencia cadastrar(EstadoOcorrencia ocorrencia) {
		return repositorio.saveAndFlush(ocorrencia);
	}

	public EstadoOcorrencia alterar(Long id, EstadoOcorrencia ocorrencia) {
		EstadoOcorrencia estadoBanco = repositorio.findById(id).get();

		if (estadoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(ocorrencia, estadoBanco, "id");

		return repositorio.saveAndFlush(estadoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<EstadoOcorrencia> buscaPorId(Long id) {
		return (Optional<EstadoOcorrencia>) repositorio.findById(id);
	}

	public List<EstadoOcorrencia> listarTodos() {
		return repositorio.findAll();
	}
}
