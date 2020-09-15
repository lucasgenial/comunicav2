package br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Recurso;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.RecursoRepository;

@Service
public class RecursoService {

	@Autowired
	private RecursoRepository repositorio;

	public Recurso cadastrar(Recurso recurso) {
		return repositorio.saveAndFlush(recurso);
	}

	public Recurso alterar(Long id, Recurso recurso) {
		Recurso recursoBanco = repositorio.findById(id).get();

		if (recursoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(recurso, recursoBanco, "id");

		return repositorio.saveAndFlush(recursoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Recurso> buscaPorId(Long id) {
		return (Optional<Recurso>) repositorio.findById(id);
	}

	public List<Recurso> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Recurso desativar(Long id, boolean situacao) {
		Recurso gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public List<Recurso> pegarPorListaDeId(List<Long> ids) {
		return repositorio.findAllByIdIn(ids);
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Recurso> listarTodosRecursos(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}
}
