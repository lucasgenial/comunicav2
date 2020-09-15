package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.HierarquiaRepository;

@Service
public class HierarquiaService {

	@Autowired
	private HierarquiaRepository repositorio;

	public Hierarquia cadastrar(Hierarquia t) {
		return repositorio.saveAndFlush(t);
	}

	public Hierarquia alterar(Long id, Hierarquia t) {
		Hierarquia hierarquiaBanco = repositorio.findById(id).get();

		if (hierarquiaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(t, hierarquiaBanco, "id");
		return repositorio.saveAndFlush(hierarquiaBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Hierarquia> buscaPorId(Long id) {
		return (Optional<Hierarquia>) repositorio.findById(id);
	}

	public List<Hierarquia> listarTodos() {
		return repositorio.findAll();
	}

	public List<Hierarquia> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Hierarquia buscaPorNome(String string) {
		if (repositorio.findByNome(string).isPresent()) {
			return repositorio.findByNome(string).get();
		}
		return null;
	}

	public List<Hierarquia> buscarPorInstituicao(Instituicao id){		
		return repositorio.findByInstituicao(id);		
	}
}
