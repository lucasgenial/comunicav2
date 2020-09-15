package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.SituacaoServidor;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.SituacaoRepository;

@Service
public class SituacaoService {

	@Autowired
	private SituacaoRepository repositorio;

	public SituacaoServidor cadastrar(SituacaoServidor SituacaoServidor) {
		return repositorio.saveAndFlush(SituacaoServidor);
	}

	public SituacaoServidor alterar(Long id, SituacaoServidor SituacaoServidor) {
		SituacaoServidor SituacaoServidorBanco = repositorio.findById(id).get();

		if (SituacaoServidorBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(SituacaoServidor, SituacaoServidorBanco, "id");

		return repositorio.saveAndFlush(SituacaoServidorBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<SituacaoServidor> buscaPorId(Long id) {
		return (Optional<SituacaoServidor>) repositorio.findById(id);
	}

	public List<SituacaoServidor> listarTodos() {
		return repositorio.findAll();
	}

	public List<SituacaoServidor> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}
}
