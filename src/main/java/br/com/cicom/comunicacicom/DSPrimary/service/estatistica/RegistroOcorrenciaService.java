package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.RegistroOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.RegistroOcorrenciaRepository;

@Service
public class RegistroOcorrenciaService {

	@Autowired
	private RegistroOcorrenciaRepository repositorio;

	public RegistroOcorrencia cadastrar(RegistroOcorrencia ocorrencia) {
		return repositorio.saveAndFlush(ocorrencia);
	}

	public RegistroOcorrencia alterar(Long id, RegistroOcorrencia ocorrencia) {
		RegistroOcorrencia registroBanco = repositorio.getOne(id);

		if (registroBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(ocorrencia, registroBanco, "id");

		return repositorio.saveAndFlush(registroBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<RegistroOcorrencia> buscaPorId(Long id) {
		return (Optional<RegistroOcorrencia>) repositorio.findById(id);
	}

	public List<RegistroOcorrencia> listarTodos() {
		return repositorio.findAll();
	}

	public List<Object[]> listaOcorrenciasGraficos(LocalDate dia, String cidade) {
		return null; //repositorio.listaOcorrencias(dia, cidade);
	}

	public List<Object[]> listaOcorrenciaPorHora(int hora, LocalDate dia, String cidade) {
		return null; //repositorio.buscaRegistroPorHora(hora, dia, cidade);
	}

	public List<Object[]> listaOcorrenciaPorAbrangencia(LocalDateTime data1, LocalDateTime data2, String cidade) {
		return null; //repositorio.buscaRegistroPorAbrangencia(data1, data2, cidade);
	}
	
	public RegistroOcorrencia buscarPeloSIC(String sic) {
		return repositorio.findByOcorrenciaSic(sic);
	}	
}
