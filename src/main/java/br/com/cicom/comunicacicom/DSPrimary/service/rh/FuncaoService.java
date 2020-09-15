package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Funcao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.FuncaoRepository;

@Service
public class FuncaoService {

	@Autowired
	private FuncaoRepository repositorio;

	public Funcao cadastrar(Funcao funcao) {
		return repositorio.saveAndFlush(funcao);
	}

	public Funcao alterar(Long id, Funcao funcao) {
		Funcao funcaoBanco = repositorio.findById(id).get();

		if (funcaoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(funcao, funcaoBanco, "id");

		return repositorio.saveAndFlush(funcaoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Funcao> buscaPorId(Long id) {
		return (Optional<Funcao>) repositorio.findById(id);
	}

	public List<Funcao> listarTodos() {
		return repositorio.findAll();
	}

	public List<Funcao> buscarPorTipoDeFuncao(TipoFuncao tipoFuncao) {
		return repositorio.findByTipoFuncao(tipoFuncao);
	}

	public List<Funcao> buscarPorPrioridadeMesa() {
		return repositorio.findByPrioridade(true);
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Funcao desativar(Long id, boolean situacao) {
		Funcao gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	
	  public DataTablesOutput<Funcao> listarTodasFuncoes(@Valid DataTablesInput input)
	  { 
		  return repositorio.findAll(input);
	  }

}
