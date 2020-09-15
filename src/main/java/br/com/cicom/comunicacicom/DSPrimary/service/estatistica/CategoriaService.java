package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;

	public Categoria cadastrar(Categoria value) {
		return repositorio.saveAndFlush(value);
	}

	public Categoria alterar(Long id, Categoria value) {
		Categoria categoriaBanco = repositorio.getOne(id);

		if (categoriaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(value, categoriaBanco, "id");

		return repositorio.saveAndFlush(categoriaBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Categoria> buscaPorId(Long id) {
		return (Optional<Categoria>) repositorio.findById(id);
	}

	public List<Categoria> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}
	
	public List<Categoria> buscarPorNome(String nome) {
		
		return repositorio.findByNome(nome);
	}

	public Categoria desativar(Long id, boolean situacao) {
		Categoria gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public List<Categoria> listarTodosPorStatus(boolean value) {
		return repositorio.findByAtivo(value);
	}
}
