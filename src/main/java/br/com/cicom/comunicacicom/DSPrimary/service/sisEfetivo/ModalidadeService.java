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

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Modalidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.ModalidadeRepository;

@Service
public class ModalidadeService {

	@Autowired
	private ModalidadeRepository repositorio;

	public Modalidade cadastrar(Modalidade modalidade) {
		return repositorio.saveAndFlush(modalidade);
	}

	public Modalidade alterar(Long id, Modalidade modalidade) {
		Modalidade modalidadeBanco = repositorio.findById(id).get();

		if (modalidadeBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(modalidade, modalidadeBanco, "id");

		return repositorio.saveAndFlush(modalidadeBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Modalidade> buscaPorId(Long id) {
		return (Optional<Modalidade>) repositorio.findById(id);
	}

	public List<Modalidade> listarTodos() {
		return repositorio.findAll();
	}

	public List<Modalidade> listarTodosPorStatus(Boolean status) {
		return repositorio.findAllByAtivo(status);
	}

	public List<Modalidade> buscarPorInstituicao(Instituicao instituicao) {
		return repositorio.findAllByInstituicoes(instituicao);
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Modalidade desativar(Long id, boolean situacao) {
		Modalidade gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Modalidade> listarTodasModalidades(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}
	
	public List<Object> buscarDadosDaTabelaGraficoEfetivo(Long idEstabelecimento){
	
		return repositorio.buscarDadosDaTabelaGraficoEfetivo(idEstabelecimento);
	}
	
}
