package br.com.cicom.comunicacicom.DSPrimary.service.sisGeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisGeral.InstituicaoRepository;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepository repositorio;

	public Instituicao cadastrar(Instituicao t) {
		return repositorio.saveAndFlush(t);
	}

	public Instituicao alterar(Long id, Instituicao t) {
		Instituicao instituicaoBanco = repositorio.findById(id).get();

		if (instituicaoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(t, instituicaoBanco, "id");

		return repositorio.saveAndFlush(instituicaoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Instituicao> buscaPorId(Long id) {
		return (Optional<Instituicao>) repositorio.findById(id);
	}

	public List<Instituicao> listarTodos() {
		return repositorio.findAll();
	}

	public Instituicao buscaPorNome(String nome) {

		if (repositorio.findByNome(nome).isPresent()) {
			return repositorio.findByNome(nome).get();
		}

		return null;
	}

	public List<Instituicao> pegarPorListaDeId(List<Long> id) {
		List<Instituicao> listaDeretorno = new ArrayList<>();

		for (Long idProcurar : id) {
			listaDeretorno.add(repositorio.findById(idProcurar).get());
		}

		return listaDeretorno;
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Instituicao> listarTodasInstituicoes(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}
}
