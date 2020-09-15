package br.com.cicom.comunicacicom.DSPrimary.service.sisGeral;

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
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisGeral.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository repositorio;

	public Estabelecimento cadastrar(Estabelecimento estabelecimento) {
		return repositorio.saveAndFlush(estabelecimento);
	}

	public Estabelecimento alterar(Long id, Estabelecimento estabelecimento) {
		Estabelecimento estabelecimentoBanco = repositorio.findById(id).get();
		
		if (estabelecimentoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(estabelecimento, estabelecimentoBanco, "id");
		return repositorio.saveAndFlush(estabelecimentoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Estabelecimento> buscaPorId(Long id) {
		return (Optional<Estabelecimento>) repositorio.findById(id);
	}

	public List<Estabelecimento> listarTodos() {
		return repositorio.findAll();
	}

	public List<Estabelecimento> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public String pegarEmail(Long id) {
		return repositorio.findById(id).get().getEmail().getEnderecoDeEmail();
	}

	public String pegarSenhaEmail(Long id) {
		return repositorio.findById(id).get().getSenhaDoEmail();
	}

	public List<Cidade> listaDeCidades(Long id) {
		return repositorio.findById(id).get().getCidades();
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Estabelecimento> listarTodosEstabelecimentos(@Valid DataTablesInput input, Usuario user) {
		
		if(!user.getGrupo().getNome().equals("ADMINISTRADOR")) {		
			input.getColumns().get(1).getSearch().setValue(String.valueOf(user.getServidor().getEstabelecimento().getNome()));
		}
			DataTablesOutput<Estabelecimento> dados = repositorio.findAll(input);
		
		return dados;
	}
}
