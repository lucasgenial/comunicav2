package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.TipoEnvolvimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.TipoEnvolvimentoRepository;

@Service
public class TipoEnvolvimentoService {

	@Autowired
	private TipoEnvolvimentoRepository repositorio;

	public TipoEnvolvimento cadastrar(TipoEnvolvimento tipoEnvolvimento) {
		return repositorio.saveAndFlush(tipoEnvolvimento);
	}

	public TipoEnvolvimento alterar(Long id, TipoEnvolvimento tipoEnvolvimento) {
		TipoEnvolvimento tipoEnvolvimentoBanco = repositorio.getOne(id);

		if (tipoEnvolvimentoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(tipoEnvolvimento, tipoEnvolvimentoBanco, "id");

		return repositorio.saveAndFlush(tipoEnvolvimentoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<TipoEnvolvimento> buscaPorId(Long id) {
		return (Optional<TipoEnvolvimento>) repositorio.findById(id);
	}

	public List<TipoEnvolvimento> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public TipoEnvolvimento desativar(Long id, boolean situacao) {
		TipoEnvolvimento gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}
}
