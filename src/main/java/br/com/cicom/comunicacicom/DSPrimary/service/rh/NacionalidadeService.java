package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Nacionalidade;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.NacionalidadeRepository;

@Service
public class NacionalidadeService {

	@Autowired
	private NacionalidadeRepository repositorio;

	public Nacionalidade cadastrar(Nacionalidade Nacionalidade) {
		return repositorio.saveAndFlush(Nacionalidade);
	}

	public Nacionalidade alterar(Long id, Nacionalidade Nacionalidade) {
		Nacionalidade NacionalidadeBanco = repositorio.findById(id).get();

		if (NacionalidadeBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(Nacionalidade, NacionalidadeBanco, "id");

		return repositorio.saveAndFlush(NacionalidadeBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Nacionalidade> buscaPorId(Long id) {
		return (Optional<Nacionalidade>) repositorio.findById(id);
	}

	public List<Nacionalidade> listarTodos() {
		return repositorio.findAll();
	}

	public List<Nacionalidade> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}
}
