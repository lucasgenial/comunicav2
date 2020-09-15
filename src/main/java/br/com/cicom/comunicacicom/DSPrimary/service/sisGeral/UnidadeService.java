package br.com.cicom.comunicacicom.DSPrimary.service.sisGeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Unidade;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisGeral.UnidadeRepository;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository repositorio;

	public Unidade cadastrar(Unidade unidade) {
		return repositorio.saveAndFlush(unidade);
	}

	public Unidade alterar(Long id, Unidade unidade) {
		Unidade unidadeBanco = repositorio.findById(id).get();

		if (unidadeBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(unidade, unidadeBanco, "id");

		return repositorio.saveAndFlush(unidadeBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Unidade> buscaPorId(Long id) {
		return (Optional<Unidade>) repositorio.findById(id);
	}

	public List<Unidade> buscarPorCidades(List<Cidade> lista) {
		List<Unidade> unidades = new ArrayList<>();
		for (Cidade for_cidade : lista) {
			unidades.addAll(repositorio.findAllByCidade(for_cidade));
		}
		return unidades;
	}

	public List<Email> buscarEmailPorCidades(List<Cidade> lista) {

		List<Email> email = new ArrayList<Email>();
		for (Unidade unidade : this.buscarPorCidades(lista)) {
			if (unidade.getEmail() != null) {
				if (!email.contains(unidade.getEmail())){
					email.add(unidade.getEmail());
				}
			}
		}
		return email;
	}

	public List<Unidade> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Unidade desativar(Long id, boolean situacao) {
		Unidade gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public List<Unidade> buscarPorListaDeInstituicao(Set<Instituicao> ListaDeInstituicoes) {
		List<Unidade> listaDeUnidades = new ArrayList<>();
		for (Instituicao instituicao : ListaDeInstituicoes) {
			listaDeUnidades.addAll(repositorio.findByInstituicao(instituicao));
		}
		return listaDeUnidades;
	}
	
	public List<Unidade> buscarPorInstituicao(Long IdInstituicao) {
		
		return repositorio.findByInstituicaoId(IdInstituicao);
	}

	public List<Unidade> buscarPorListaDeInstituicaoEEstabelecimento(Set<Instituicao> ListaDeInstituicoes,
			Estabelecimento estabelecimento) {

		return repositorio.findAllByInstituicaoInAndEstabelecimento(ListaDeInstituicoes, estabelecimento);
	}
	
	public List<Unidade> buscarPorInstituicaoEEstabelecimento(Long idInstituicao, Long idEstabelecimento) {

		return repositorio.findAllByInstituicaoIdAndEstabelecimentoId(idInstituicao, idEstabelecimento);
	}


	public List<Unidade> listaTodosPorEstabelecimento(Set<Estabelecimento> estabelecimento) {
		return repositorio.findByEstabelecimentoIn(estabelecimento);
	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Unidade> listarTodasUnidades(@Valid DataTablesInput input, Usuario user) {
		//return repositorio.findAll(input);
		if(!user.getGrupo().getNome().equals("ADMINISTRADOR")) {		
			input.getColumns().get(2).getSearch().setValue(String.valueOf(user.getServidor().getEstabelecimento().getNome()));
		}
			DataTablesOutput<Unidade> dados = repositorio.findAll(input);
		
		return dados;
	}
}
