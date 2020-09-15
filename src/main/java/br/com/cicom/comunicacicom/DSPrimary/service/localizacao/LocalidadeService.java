package br.com.cicom.comunicacicom.DSPrimary.service.localizacao;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.repository.localizacao.LocalidadeRepository;

@Service
public class LocalidadeService {

	@Autowired
	private LocalidadeRepository repositorio;


	public Localidade cadastrar(Localidade localidade) {
		return repositorio.saveAndFlush(localidade);
	}

	public Localidade alterar(Long id, Localidade localidade) {
		Localidade localidadeBanco = repositorio.findById(id).get();

		if (localidadeBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(localidade, localidadeBanco, "id");

		return repositorio.saveAndFlush(localidadeBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Localidade> buscaPorId(Long id) {
		return (Optional<Localidade>) repositorio.findById(id);
	}

	public List<Localidade> buscarPorCidade(Cidade cidade) {
		return repositorio.findByCidade(cidade);
	}

	public Localidade buscarPorCidadeeNome(Cidade cidade, String nome) {
		return repositorio.findByCidadeAndNome(cidade, nome);
	}

	public Localidade obterAlgum(Cidade cidade) {
		return repositorio.findByCidade(cidade).get(0);
	}

	public List<Localidade> listarTodos() {
		return repositorio.findAll();
	}

	public List<Localidade> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Localidade> listarTodasLocalidades(@Valid DataTablesInput input, Usuario user) {
		if(!user.getGrupo().getNome().equals("ADMINISTRADOR")) {		
			input.getColumns().get(3).getSearch().setValue(String.valueOf(user.getServidor().getEstabelecimento().getNome()));
		}
		DataTablesOutput<Localidade> dados = repositorio.findAll(input);
		
		return dados;
	}
}
