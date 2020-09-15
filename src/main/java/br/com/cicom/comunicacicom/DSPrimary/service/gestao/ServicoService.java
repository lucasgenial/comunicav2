package br.com.cicom.comunicacicom.DSPrimary.service.gestao;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Servico;
import br.com.cicom.comunicacicom.DSPrimary.repository.gestao.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository repositorio;

	public Servico cadastrar(Servico servico) {
		return repositorio.saveAndFlush(servico);
	}

	public Servico alterar(Long id, Servico servico) {
		Servico ServicoBanco = repositorio.getOne(id);

		if (ServicoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(servico, ServicoBanco, "id");

		return repositorio.saveAndFlush(ServicoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public List<Servico> listarTodos() {
		return repositorio.findAll();

	}

	public Optional<Servico> buscaPorId(Long id) {
		return (Optional<Servico>) repositorio.findById(id);
	}

	public Page<Servico> buscaPaginada(String pesquisa, Pageable pageable) {
		return null;
	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Servico> listarTodosServicos(@Valid DataTablesInput input) {
		return repositorio.findAll(input);	
	}

	public Servico buscarPorNome(String nome) {
		return repositorio.findByNome(nome);
	}
}
