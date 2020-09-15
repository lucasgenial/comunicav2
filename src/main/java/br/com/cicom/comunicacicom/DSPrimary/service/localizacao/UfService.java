package br.com.cicom.comunicacicom.DSPrimary.service.localizacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.UnidadeFederativa;
import br.com.cicom.comunicacicom.DSPrimary.repository.localizacao.UfRepository;

@Service
public class UfService {

	@Autowired
	private UfRepository repositorio;

	public UnidadeFederativa cadastrar(UnidadeFederativa UnidadeFederativa) {
		return repositorio.saveAndFlush(UnidadeFederativa);
	}

	public UnidadeFederativa alterar(Long id, UnidadeFederativa UnidadeFederativa) {
		UnidadeFederativa UnidadeFederativaBanco = repositorio.findById(id).get();

		if (UnidadeFederativaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(UnidadeFederativa, UnidadeFederativaBanco, "id");

		return repositorio.saveAndFlush(UnidadeFederativaBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
		;
	}

	public Optional<UnidadeFederativa> buscaPorId(Long id) {
		return (Optional<UnidadeFederativa>) repositorio.findById(id);
	}

	public List<UnidadeFederativa> listarTodos() {
		return repositorio.findAll();
	}

	public List<UnidadeFederativa> listarTodosPorStatus(Boolean status) {
		return repositorio.findAllByAtivo(status);
	}

	public List<UnidadeFederativa> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	/* BUSCAR ESTADO POR NOME */
	public UnidadeFederativa buscarPorNome(String nome) {
		return repositorio.findByNomeIgnoreCase(nome);
	}
}
