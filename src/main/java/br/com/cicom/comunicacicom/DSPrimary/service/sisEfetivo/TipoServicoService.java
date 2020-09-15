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

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoServico;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.TipoServicoRepository;

@Service
public class TipoServicoService {

	@Autowired
	private TipoServicoRepository repositorio;

	public TipoServico cadastrar(TipoServico tipoServico) {
		return repositorio.saveAndFlush(tipoServico);
	}

	public TipoServico alterar(Long id, TipoServico tipoServico) {
		TipoServico tipoServicoBanco = repositorio.findById(id).get();

		if (tipoServicoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(tipoServico, tipoServicoBanco, "id");

		return repositorio.saveAndFlush(tipoServicoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<TipoServico> buscaPorId(Long id) {
		return (Optional<TipoServico>) repositorio.findById(id);
	}

	public List<TipoServico> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public TipoServico desativar(Long id, boolean situacao) {
		TipoServico gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<TipoServico> listarTodosTiposServico(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}
}
