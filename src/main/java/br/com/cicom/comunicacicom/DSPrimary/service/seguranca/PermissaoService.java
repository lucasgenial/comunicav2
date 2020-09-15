package br.com.cicom.comunicacicom.DSPrimary.service.seguranca;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.repository.seguranca.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository repositorio;


	public Permissao cadastrar(Permissao permissao) {
		return repositorio.saveAndFlush(permissao);
	}

	public Permissao alterar(Long id, Permissao permissao) {
		Permissao permissaoBanco = repositorio.findById(id).get();

		if (permissaoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(permissao, permissaoBanco, "id");

		return repositorio.saveAndFlush(permissaoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Permissao> buscaPorId(Long id) {
		return (Optional<Permissao>) repositorio.findById(id);
	}

	public List<Permissao> listarTodos() {
		return repositorio.findAll();
	}
	
	
	public Long[] listarIdsDePermissoesDeUmGrupo(Long idGrupo) {
		return repositorio.listaDeIdsDePermissoesDeUmGrupo(idGrupo);
	}

	public Permissao desativar(Long id, boolean situacao) {
		Permissao gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Permissao> listarTodasPermissoes(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}

}
